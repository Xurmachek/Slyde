package dev.jfronny.slyde.mixin.sodium;

import dev.jfronny.slyde.Slyde;
import net.caffeinemc.mods.sodium.client.gui.ColorTheme;
import net.caffeinemc.mods.sodium.client.gui.options.control.AbstractOptionList;
import net.caffeinemc.mods.sodium.client.gui.options.control.ControlElement;
import net.caffeinemc.mods.sodium.client.util.Dim2i;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.caffeinemc.mods.sodium.client.gui.options.control.SliderControl$SliderControlElement")
public abstract class SodiumSliderControlMixin extends ControlElement {
    @Shadow(remap = false) private boolean sliderHeld;

    public SodiumSliderControlMixin(AbstractOptionList list, Dim2i dim, ColorTheme theme) {
        super(list, dim, theme);
    }

    @Redirect(method = "getThumbPositionForValue(I)D", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(DDD)D"))
    private double slyde$adjustClamp1(double value, double min, double max) {
        return Slyde.isActive() ? value : Mth.clamp(value, min, max);
    }

    @Redirect(method = "setValueFromMouse(D)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(DDD)D"))
    private double slyde$adjustClamp2(double value, double min, double max) {
        return Slyde.isActive() ? value : Mth.clamp(value, min, max);
    }

    @Inject(method = "mouseClicked(Lnet/minecraft/client/input/MouseButtonEvent;Z)Z", at = @At("RETURN"))
    private void slyde$onClick(MouseButtonEvent event, boolean repeated, CallbackInfoReturnable<Boolean> cir) {
        Slyde.setScrolling(sliderHeld);
    }

    @Inject(method = "mouseReleased(Lnet/minecraft/client/input/MouseButtonEvent;)Z", at = @At("RETURN"))
    private void slyde$onRelease(MouseButtonEvent event, CallbackInfoReturnable<Boolean> cir) {
        Slyde.setScrolling(sliderHeld);
    }
}
