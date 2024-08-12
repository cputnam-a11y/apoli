package io.github.apace100.apoli.power.type;

import io.github.apace100.apoli.Apoli;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.factory.PowerTypeFactory;
import io.github.apace100.apoli.util.HudRender;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.Pair;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class SelfActionOnKillPowerType extends CooldownPowerType {

    private final Predicate<Pair<DamageSource, Float>> damageCondition;
    private final Predicate<Entity> targetCondition;
    private final Consumer<Entity> entityAction;

    public SelfActionOnKillPowerType(Power power, LivingEntity entity, Consumer<Entity> entityAction, Predicate<Entity> targetCondition, Predicate<Pair<DamageSource, Float>> damageCondition, HudRender hudRender, int cooldownDuration) {
        super(power, entity, cooldownDuration, hudRender);
        this.damageCondition = damageCondition;
        this.entityAction = entityAction;
        this.targetCondition = targetCondition;
    }

    public boolean doesApply(Entity target, DamageSource source, float amount) {
        return this.canUse()
            && (targetCondition == null || targetCondition.test(target))
            && (damageCondition == null || damageCondition.test(new Pair<>(source, amount)));
    }

    public void executeAction() {
        this.use();
        entityAction.accept(entity);
    }

    public static PowerTypeFactory<?> getFactory() {
        return new PowerTypeFactory<>(
            Apoli.identifier("self_action_on_kill"),
            new SerializableData()
                .add("entity_action", ApoliDataTypes.ENTITY_ACTION)
                .add("target_condition", ApoliDataTypes.ENTITY_CONDITION, null)
                .add("damage_condition", ApoliDataTypes.DAMAGE_CONDITION, null)
                .add("hud_render", ApoliDataTypes.HUD_RENDER, HudRender.DONT_RENDER)
                .add("cooldown", SerializableDataTypes.INT, 1),
            data -> (power, entity) -> new SelfActionOnKillPowerType(power, entity,
                data.get("entity_action"),
                data.get("target_condition"),
                data.get("damage_condition"),
                data.get("hud_render"),
                data.get("cooldown")
            )
        ).allowCondition();
    }
}