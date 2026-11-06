package dev.jfronny.slyde.mixin;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import net.minecraft.client.OptionInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(OptionInstance.UnitDouble.class)
public class DoubleSliderCallbacksMixin {
    @Redirect(method = "codec", at = @At(value = "INVOKE", target = "Lcom/mojang/serialization/Codec;doubleRange(DD)Lcom/mojang/serialization/Codec;", remap = false))
    private Codec<Double> slyde$returnRangelessCodec(double minInclusive, double maxInclusive) {
        return Codec.either(Codec.DOUBLE, Codec.BOOL)
                .xmap(either -> either.map(value -> value, value -> value ? 1.0 : 0.0), Either::left);
    }

    @Inject(method = "validateValue(Ljava/lang/Double;)Ljava/util/Optional;", at = @At("HEAD"), cancellable = true)
    private void slyde$skipValidate(Double double_, CallbackInfoReturnable<Optional<Double>> cir) {
        cir.setReturnValue(Optional.of(double_));
    }
}
