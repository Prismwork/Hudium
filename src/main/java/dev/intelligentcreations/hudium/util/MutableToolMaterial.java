package dev.intelligentcreations.hudium.util;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class MutableToolMaterial implements ToolMaterial {
    public int miningLevel;

    public MutableToolMaterial(int miningLevel) {
        this.miningLevel = miningLevel;
    }

    @Override
    public int getDurability() {
        return 1;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 1.01f;
    }

    @Override
    public float getAttackDamage() {
        return 0;
    }

    @Override
    public int getMiningLevel() {
        return miningLevel;
    }

    @Override
    public int getEnchantability() {
        return 1;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.EMPTY;
    }
}
