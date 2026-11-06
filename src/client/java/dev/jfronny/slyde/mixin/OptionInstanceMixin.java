package dev.jfronny.slyde.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.OptionInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(OptionInstance.class)
public abstract class OptionInstanceMixin<T> {
    @Shadow public abstract OptionInstance.ValueSet<T> values();

    @WrapOperation(method = "set(Ljava/lang/Object;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/OptionInstance$ValueSet;validateValue(Ljava/lang/Object;)Ljava/util/Optional;"))
    Optional<T> maybeDontValidate(OptionInstance.ValueSet<T> instance, T t, Operation<Optional<T>> original) {
        if (values() instanceof OptionInstance.SliderableValueSet) {
            return Optional.of(t);
        } else {
            return original.call(instance, t);
        }
    }
}
