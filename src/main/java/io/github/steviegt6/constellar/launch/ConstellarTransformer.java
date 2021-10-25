package io.github.steviegt6.constellar.launch;

import io.github.steviegt6.constellar.launch.srg.MappedMember;
import net.minecraft.launchwrapper.IClassTransformer;
import optifine.OptiFineClassTransformer;
import org.apache.commons.io.FileUtils;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ConstellarTransformer implements IClassTransformer {
    public final File NotchMcpSrg;
    public final String SrgContents;
    public final OptiFineClassTransformer Transformer;
    public List<MappedMember> Mapping;

    public ConstellarTransformer() {
        String temp;
        NotchMcpSrg = new File(System.getProperty("net.minecraftforge.gradle.GradleStart.srg.mcp-srg"));
        Transformer = new OptiFineClassTransformer();

        try {
            temp = FileUtils.readFileToString(NotchMcpSrg);
        } catch (IOException e) {
            temp = "null";
            e.printStackTrace();
        }
        SrgContents = temp;
    }

    @Override
    public byte[] transform(@NonNull String name, @NonNull String transformedName, byte[] basicClass) {
        if (Objects.equals(SrgContents, "temp"))
            return basicClass;

        Optional<MappedMember> member = Mapping.stream().filter(x -> Objects.equals(x.DeObfuscatedName, transformedName)).findFirst();
        member.ifPresent(mappedMember -> Transformer.transform(mappedMember.ObfuscatedName, mappedMember.ObfuscatedName, basicClass));

        return basicClass;
    }
}
