package dev.jfronny.slyde.mixin.sodium;

import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.input.MouseButtonEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(ContainerEventHandler.class)
public interface ContainerEventHandlerMixin {
    @Shadow Optional<GuiEventListener> getChildAt(double mouseX, double mouseY);

    /**
     * Needed for the sodium slider control to actually unlock the mouse when released off-window.
     */
    @Inject(method = "mouseReleased(Lnet/minecraft/client/input/MouseButtonEvent;)Z", at = @At("RETURN"), cancellable = true)
    default void slyde$propagateReleased(MouseButtonEvent click, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) return;
        GuiEventListener hovered = getChildAt(click.x(), click.y()).orElse(null);
        if (hovered == null) return;
        cir.setReturnValue(hovered.mouseReleased(click));
    }
}
