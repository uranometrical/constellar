package io.github.steviegt6.constellar.launch;

import net.minecraft.launchwrapper.IClassTransformer;

public class ConstellarTransformer implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        return basicClass;
    }
}
