package lilypuree.decorative_blocks.registration;

import com.google.common.collect.ImmutableMap;
import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.blocks.types.VanillaWoodTypes;
import lilypuree.decorative_blocks.items.SeatItem;
import lilypuree.decorative_blocks.items.SupportItem;
import lilypuree.decorative_blocks.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

import java.util.function.Function;

import static lilypuree.decorative_blocks.blocks.types.WoodDecorativeBlockTypes.*;

public class DBItems {
    public static final ItemWrapper<BlockItem> CHANDELIER;
    public static final ItemWrapper<BlockItem> SOUL_CHANDELIER;
    public static final ItemWrapper<BlockItem> BRAZIER;
    public static final ItemWrapper<BlockItem> SOUL_BRAZIER;
    public static final ItemWrapper<BlockItem> BAR_PANEL;
    public static final ItemWrapper<BlockItem> LATTICE;
    public static final ItemWrapper<BlockItem> CHAIN;
    public static final ItemWrapper<BlockItem> STONE_PILLAR;
    public static final ItemWrapper<BlockItem> ROCKY_DIRT;

    public static final ImmutableMap<WoodType, ItemWrapper<BlockItem>> BEAM_ITEMBLOCKS;
    public static final ImmutableMap<WoodType, ItemWrapper<SeatItem>> SEAT_ITEMBLOCKS;
    public static final ImmutableMap<WoodType, ItemWrapper<SupportItem>> SUPPORT_ITEMBLOCKS;
    public static final ImmutableMap<WoodType, ItemWrapper<BlockItem>> PALISADE_ITEMBLOCKS;


    static {
        CHANDELIER = registerBlockItem("chandelier", DBBlocks.CHANDELIER, new Item.Properties().cookingFuel(ContextIntProviders.COOKING_TIME_COAL));
        SOUL_CHANDELIER = registerBlockItem("soul_chandelier", DBBlocks.SOUL_CHANDELIER, new Item.Properties().cookingFuel(ContextIntProviders.COOKING_TIME_COAL));
        BRAZIER = registerBlockItem("brazier", DBBlocks.BRAZIER);
        SOUL_BRAZIER = registerBlockItem("soul_brazier", DBBlocks.SOUL_BRAZIER);
        BAR_PANEL = registerBlockItem("bar_panel", DBBlocks.BAR_PANEL);
        LATTICE = registerBlockItem("lattice", DBBlocks.LATTICE, new Item.Properties().cookingFuel(ContextIntProviders.COOKING_TIME_WOOD_ITEMS_EXTRA_SMALL));
        CHAIN = registerBlockItem("chain", DBBlocks.CHAIN);
        STONE_PILLAR = registerBlockItem("stone_pillar", DBBlocks.STONE_PILLAR);
        ROCKY_DIRT = registerBlockItem("rocky_dirt", DBBlocks.ROCKY_DIRT);

        ImmutableMap.Builder<WoodType, ItemWrapper<BlockItem>> beams = new ImmutableMap.Builder<>();
        ImmutableMap.Builder<WoodType, ItemWrapper<BlockItem>> palisades = new ImmutableMap.Builder<>();
        ImmutableMap.Builder<WoodType, ItemWrapper<SupportItem>> supports = new ImmutableMap.Builder<>();
        ImmutableMap.Builder<WoodType, ItemWrapper<SeatItem>> seats = new ImmutableMap.Builder<>();
        for (WoodType woodType : VanillaWoodTypes.VANILLA) {
            if (woodType != WoodType.BAMBOO)
                beams.put(woodType, registerBlockItem(DBNames.name(woodType, BEAM), DBBlocks.BEAMS.get(woodType), woodFuelProperties(woodType)));
            seats.put(woodType, registerItem(DBNames.name(woodType, SEAT), woodFuelProperties(woodType), (properties) -> new SeatItem(DBBlocks.SEATS.get(woodType).get(), properties)));
            supports.put(woodType, registerItem(DBNames.name(woodType, SUPPORT), woodFuelProperties(woodType), (properties) -> new SupportItem(DBBlocks.SUPPORTS.get(woodType).get(), properties)));
            palisades.put(woodType, registerBlockItem(DBNames.name(woodType, PALISADE), DBBlocks.PALISADES.get(woodType), woodFuelProperties(woodType)));
        }
        BEAM_ITEMBLOCKS = beams.build();
        PALISADE_ITEMBLOCKS = palisades.build();
        SUPPORT_ITEMBLOCKS = supports.build();
        SEAT_ITEMBLOCKS = seats.build();
    }

    public static void init() {
    }


    private static <T extends Item> ItemWrapper<T> registerItem(String name, Item.Properties properties, Function<Item.Properties, T> itemSupplier) {
        final var id = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Constants.MOD_ID, name));
        properties.setId(id);
        return new ItemWrapper<>(Services.PLATFORM.register(BuiltInRegistries.ITEM, name, () -> itemSupplier.apply(properties)));
    }

    private static ItemWrapper<BlockItem> registerBlockItem(String name, BlockWrapper<?> block) {
        return registerBlockItem(name, block, new Item.Properties());
    }

    private static ItemWrapper<BlockItem> registerBlockItem(String name, BlockWrapper<?> block, Item.Properties properties) {
        return registerItem(name, properties, (itemProperties) -> new BlockItem(block.get(), itemProperties));
    }

    private static Item.Properties woodFuelProperties(WoodType woodType) {
        Item.Properties properties = new Item.Properties();
        if (!VanillaWoodTypes.isNetherWood(woodType))
            properties.cookingFuel(ContextIntProviders.COOKING_TIME_WOOD_ITEMS_SMALL);
        return properties;
    }

}
