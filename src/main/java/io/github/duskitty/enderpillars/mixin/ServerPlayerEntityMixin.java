package io.github.duskitty.enderpillars.mixin;

import io.github.duskitty.enderpillars.container.WarpStorage;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin
{
    @Inject(at = @At("TAIL"), method = "readCustomDataFromTag")
    public void onReadCustomDataFromTag(CompoundTag tag, CallbackInfo ci)
    {
        WarpStorage.readNBT((ServerPlayerEntity)(Object)this, tag);
    }
    @Inject(at = @At("TAIL"), method = "writeCustomDataToTag")
    public void onWriteCustomDataToTag(CompoundTag tag, CallbackInfo ci)
    {
        WarpStorage.writeNBT((ServerPlayerEntity)(Object)this, tag);
    }
}