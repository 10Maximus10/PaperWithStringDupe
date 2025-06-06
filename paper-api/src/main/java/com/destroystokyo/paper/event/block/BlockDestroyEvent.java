package com.destroystokyo.paper.event.block;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockExpEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

/**
 * Fired anytime the server intends to 'destroy' a block through some triggering reason.
 * This does not fire anytime a block is set to air, but only with more direct triggers such
 * as physics updates, pistons, entities changing blocks, commands set to "Destroy".
 * <p>
 * This event is associated with the game playing a sound effect at the block in question, when
 * something can be described as "intend to destroy what is there",
 * <p>
 * Events such as leaves decaying, pistons retracting (where the block is moving), does NOT fire this event.
 */
@NullMarked
public class BlockDestroyEvent extends BlockExpEvent implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final BlockData newState;
    private boolean willDrop;
    private boolean playEffect = true;
    private BlockData effectBlock;

    private boolean cancelled;

    @ApiStatus.Internal
    public BlockDestroyEvent(final Block block, final BlockData newState, final BlockData effectBlock, final int xp, final boolean willDrop) {
        super(block, xp);
        this.newState = newState;
        this.effectBlock = effectBlock;
        this.willDrop = willDrop;
    }

    /**
     * Gets the effect that will be played when the block is broken.
     *
     * @return block break effect
     */
    public BlockData getEffectBlock() {
        return this.effectBlock;
    }

    /**
     * Sets the effect that will be played when the block is broken.
     * Note: {@link BlockDestroyEvent#playEffect()} must be {@code true} in order for this effect to be
     * played.
     *
     * @param effectBlock block effect
     */
    public void setEffectBlock(final BlockData effectBlock) {
        this.effectBlock = effectBlock.clone();
    }

    /**
     * @return The new state of this block (Air, or a Fluid type)
     */
    public BlockData getNewState() {
        return this.newState.clone();
    }

    /**
     * @return If the server is going to drop the block in question with this destroy event
     */
    public boolean willDrop() {
        return this.willDrop;
    }

    /**
     * @param willDrop If the server is going to drop the block in question with this destroy event
     */
    public void setWillDrop(final boolean willDrop) {
        this.willDrop = willDrop;
    }

    /**
     * @return If the server is going to play the sound effect for this destruction
     */
    public boolean playEffect() {
        return this.playEffect;
    }

    /**
     * @param playEffect If the server should play the sound effect for this destruction
     */
    public void setPlayEffect(final boolean playEffect) {
        this.playEffect = playEffect;
    }

    /**
     * @return If the event is cancelled, meaning the block will not be destroyed
     */
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    /**
     * If the event is cancelled, the block will remain in its previous state.
     */
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
