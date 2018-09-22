package pyre.coloredredstone.init;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import pyre.coloredredstone.entities.EntityItemColored;
import pyre.coloredredstone.util.Reference;

import java.util.ArrayList;
import java.util.List;

public class ModEntities {

    public static final List<EntityEntry> ENTITY_ENTRIES = new ArrayList<>();

    public static EntityEntry ENTITY_ITEM_COLORED_ENTRY;

    private static int entityId = 0;

    public static void init(){
        ENTITY_ITEM_COLORED_ENTRY = createEntityEntry("entity_item_colored", 64, 20, true);
        ENTITY_ENTRIES.add(ENTITY_ITEM_COLORED_ENTRY);
    }

    private static EntityEntry createEntityEntry(String entityName, int trackingRange, int updateFrequency, boolean sendVelocityUpdate){
        ResourceLocation registryName = new ResourceLocation(Reference.MOD_ID, entityName);
        return EntityEntryBuilder.create()
                .entity(EntityItemColored.class)
                .id(registryName, entityId++)
                .name(entityName)
                .tracker(trackingRange, updateFrequency, sendVelocityUpdate)
                .build();
    }
}
