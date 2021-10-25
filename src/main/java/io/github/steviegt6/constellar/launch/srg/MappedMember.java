package io.github.steviegt6.constellar.launch.srg;

import java.util.ArrayList;
import java.util.List;

public class MappedMember {
    public MappingType Type;
    public String ObfuscatedName;
    public String DeObfuscatedName;

    public MappedMember(String entry) {
        String[] content = entry.split(" ");
    }

    public static List<MappedMember> createMappings(String contents) {
        List<MappedMember> mappings = new ArrayList<>();

        return mappings;
    }
}
