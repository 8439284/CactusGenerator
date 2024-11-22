package org.ajls.cactusgenerator.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EntityAndLiquidEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    Entity entity;
    Block block;
    Material material;

    EntityAndLiquidEvent(Entity entity, Material material, Block block) {
        this.entity = entity;
        this.block = block;
        this.material = material;
    }

    EntityAndLiquidEvent(Entity entity, Material material) {
        this.entity = entity;
        this.material = material;
    }

    EntityAndLiquidEvent(Entity entity) {
        this.entity = entity;
        block = entity.getLocation().getBlock();
    }

    public Entity getEntity() {
        return entity;
    }

    public Block getBlock() {
        return block;
    }
    public Material getMaterial() {
        return material;
    }
}
