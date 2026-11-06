package dev.jfronny.slyde.mixin;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.function.Function;

@Mixin(Codec.class)
public interface CodecMixin {
    /**
     * @author JFronny
     * @reason No, don't check anything
     */
    @Overwrite(remap = false)
    static <N extends Number & Comparable<N>> Function<N, DataResult<N>> checkRange(final N minInclusive, final N maxInclusive) {
        return DataResult::success;
    }
}
