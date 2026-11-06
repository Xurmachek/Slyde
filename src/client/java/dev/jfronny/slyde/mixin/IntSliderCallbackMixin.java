package dev.jfronny.slyde.mixin;

import net.minecraft.client.OptionInstance;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(targets = "net.minecraft.client.OptionInstance$IntRangeBase")
public interface IntSliderCallbackMixin extends OptionInstance.SliderableValueSet<Integer> {
    @Shadow int minInclusive();
    @Shadow int maxInclusive();

    /**
     * @author JFronny
     * @reason prevent toValue from limiting us to the upper bound
     */
    @Override
    @Overwrite
    default Integer fromSliderValue(double sliderProgress) {
        if (sliderProgress >= 1.0 && sliderProgress < 1.00001f) {
            sliderProgress = 0.99999f;
        }
        return Mth.floor(Mth.map(sliderProgress, 0.0, 1.0, this.minInclusive(), this.maxInclusive() + 1.0));
    }
}
