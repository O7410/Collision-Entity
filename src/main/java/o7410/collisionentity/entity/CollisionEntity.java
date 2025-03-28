package o7410.collisionentity.entity;

import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.AbstractNbtNumber;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class CollisionEntity extends Entity {
    private static final TrackedData<Vector3f> SIZE = DataTracker.registerData(CollisionEntity.class, TrackedDataHandlerRegistry.VECTOR3F);
    private static final String SIZE_KEY = "size";

    public CollisionEntity(EntityType<?> type, World world) {
        super(type, world);
        this.noClip = true;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        builder.add(SIZE, new Vector3f(1));
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.get(SIZE_KEY) instanceof NbtList nbtList && nbtList.size() == 3 &&
                nbtList.get(0) instanceof AbstractNbtNumber xSize &&
                nbtList.get(1) instanceof AbstractNbtNumber ySize &&
                nbtList.get(2) instanceof AbstractNbtNumber zSize) {
            this.dataTracker.set(SIZE, new Vector3f(xSize.floatValue(), ySize.floatValue(), zSize.floatValue()));
            this.setBoundingBox(this.calculateBoundingBox());
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        Vector3f size = this.dataTracker.get(SIZE);
        nbt.put(SIZE_KEY, toNbtList(size.x, size.y, size.z));
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        super.onTrackedDataSet(data);
        if (SIZE.equals(data)) {
            this.setBoundingBox(this.calculateBoundingBox());
        }
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    @Override
    public boolean canBeHitByProjectile() {
        return true;
    }

    @Override
    public PistonBehavior getPistonBehavior() {
        return PistonBehavior.IGNORE;
    }

    @Override
    public boolean canAvoidTraps() {
        return true;
    }

    @Override
    public void tick() {
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        return EntityDimensions.fixed(0, 0);
    }

    @Override
    protected Box calculateBoundingBox() {
        Vector3f size = this.dataTracker.get(SIZE);
        Vec3d pos = this.getPos();
        float halfXSize = size.x / 2;
        float halfZSize = size.z / 2;
        return new Box(
                pos.x - halfXSize,
                pos.y,
                pos.z - halfZSize,
                pos.x + halfXSize,
                pos.y + size.y,
                pos.z + halfZSize
        );
    }
}
