package dev.jfronny.slyde.mixin;

import dev.jfronny.slyde.Slyde;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSliderButton.class)
public abstract class AbstractSliderButtonMixin {
    @Redirect(method = "setValue(D)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(DDD)D"))
    private double slyde$adjustClamp(double value, double min, double max) {
        return Slyde.isActive() ? value : Mth.clamp(value, min, max);
    }

    @Inject(method = "onClick(Lnet/minecraft/client/input/MouseButtonEvent;Z)V", at = @At("TAIL"))
    private void slyde$onClick(MouseButtonEvent click, boolean doubled, CallbackInfo ci) {
        Slyde.setScrolling(true);
    }

    @Inject(method = "onRelease(Lnet/minecraft/client/input/MouseButtonEvent;)V", at = @At("TAIL"))
    private void slyde$onRelease(MouseButtonEvent click, CallbackInfo ci) {
        Slyde.setScrolling(false);
    }
}
