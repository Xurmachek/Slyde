package dev.jfronny.slyde.mixin;

import com.mojang.serialization.Codec;
import net.minecraft.client.OptionInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(OptionInstance.IntRange.class)
public class ValidatingIntSliderCallbacksMixin {
    @Inject(method = "codec()Lcom/mojang/serialization/Codec;", at = @At("HEAD"), cancellable = true)
    private void slyde$returnRangelessCodec(CallbackInfoReturnable<Codec<Integer>> cir) {
        cir.setReturnValue(Codec.INT);
    }

    @Inject(method = "validateValue(Ljava/lang/Integer;)Ljava/util/Optional;", at = @At("HEAD"), cancellable = true)
    private void slyde$skipValidate(Integer integer, CallbackInfoReturnable<Optional<Integer>> cir) {
        cir.setReturnValue(Optional.of(integer));
    }
}
