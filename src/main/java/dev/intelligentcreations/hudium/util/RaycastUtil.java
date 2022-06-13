package dev.intelligentcreations.hudium.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class RaycastUtil {
    public static HitResult getEntityHitResult(Entity source, float tickDelta) {
        Vec3d rotationVec = source.getRotationVec(tickDelta);
        Vec3d start = source.getClientCameraPosVec(tickDelta);
        Vec3d end = start.add(rotationVec.x * 4, rotationVec.y * 4, rotationVec.z * 4);
        HitResult result = ProjectileUtil.raycast(source, start, end, new Box(start, end), EntityPredicates.VALID_LIVING_ENTITY, 0f);
        if (result == null) return MISS;
        return result;
    }

    public static HitResult getBlockHitResult(Entity source, float tickDelta) {
        HitResult result = source.raycast(4, tickDelta, true);
        if (result == null) return MISS;
        return result;
    }

    private static final HitResult MISS = new HitResult(Vec3d.ZERO) {
        @Override
        public Type getType() {
            return Type.MISS;
        }
    };
}
