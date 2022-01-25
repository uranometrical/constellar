package dev.tomat.bridge;

/**
 * Base interface for bridges.
 */
public interface IBridge {
    /**
     * The package the bridge associates with (i.e. "net.minecraft").
     * @return The associated module package.
     */
    String getPackageAssociation();
}
