package dev.jfronny.slyde.mixin.sodium;

import net.caffeinemc.mods.sodium.api.config.option.SteppedValidator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SteppedValidator.class)
public interface SodiumSteppedValidatorMixin {
    @Shadow int min();
    @Shadow int step();

    @Inject(method = "isValueValid(I)Z", at = @At("RETURN"), cancellable = true)
    default void adjustValid(int value, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue() && (value - this.min()) % this.step() == 0) {
            cir.setReturnValue(true);
        }
    }
}
