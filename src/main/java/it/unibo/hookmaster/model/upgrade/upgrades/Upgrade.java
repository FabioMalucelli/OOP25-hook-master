package it.unibo.hookmaster.model.upgrade.upgrades;

/**
 * Interface that models an upgrade.
 */
public interface Upgrade {

    /**
     * Retrives the upgrade name.
     * 
     * @return the upgrade name
     */
    String getName();

    /**
     * Retrives the upgrade description.
     * 
     * @return the upgrade description
     */
    String getDescription();

    /**
     * Retrives the upgrade level.
     * 
     * @return the upgrade level
     */
    int getLevel();

    /**
     * Retrives the upgrade value.
     * 
     * @return the upgrade value
     */
    double getValue();

    /**
     * Increase the upgrade level.
     */
    void upgrade();

}
