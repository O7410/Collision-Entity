package o7410.collisionentity;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import o7410.collisionentity.entity.CollisionEntity;

public class CollisionEntityMod implements ModInitializer {
	public static final String MOD_ID = "collision-entity";

	public static EntityType<CollisionEntity> COLLISION = Registry.register(Registries.ENTITY_TYPE,
			Identifier.of(CollisionEntityMod.MOD_ID, "collision"),
			EntityType.Builder.create(CollisionEntity::new, SpawnGroup.MISC)
					.dimensions(0.0f, 0.0f)
					.maxTrackingRange(10)
					.build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(CollisionEntityMod.MOD_ID, "collision"))));

    @Override
	public void onInitialize() {
	}
}