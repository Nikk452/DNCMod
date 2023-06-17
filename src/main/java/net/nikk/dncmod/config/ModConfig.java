package net.nikk.dncmod.config;

public class ModConfig {
    public int xp_per_lvl_multi;
    public boolean isInPounds;
    public boolean isRace_human_approved;
    public boolean isRace_dwarf_approved;
    public boolean isRace_elf_approved;
    public boolean isClass_fighter_approved;
    public boolean isClass_druid_approved;
    public boolean isClass_cleric_approved;
    public boolean isClass_wizard_approved;
    public boolean isClass_sorcerer_approved;
    public boolean isClass_monk_approved;

    public int config_ver = 2;

    public ModConfig(int xp_per_lvl_multi, boolean isInPounds, boolean isRace_human_approved,
                     boolean isRace_dwarf_approved, boolean isRace_elf_approved,
                     boolean isClass_fighter_approved, boolean isClass_druid_approved,
                     boolean isClass_cleric_approved, boolean isClass_wizard_approved,
                     boolean isClass_sorcerer_approved, boolean isClass_monk_approved){
        this.xp_per_lvl_multi = xp_per_lvl_multi;
        this.isInPounds = isInPounds;
        this.isRace_human_approved = isRace_human_approved;
        this.isRace_dwarf_approved = isRace_dwarf_approved;
        this.isRace_elf_approved = isRace_elf_approved;
        this.isClass_fighter_approved = isClass_fighter_approved;
        this.isClass_druid_approved = isClass_druid_approved;
        this.isClass_cleric_approved = isClass_cleric_approved;
        this.isClass_wizard_approved = isClass_wizard_approved;
        this.isClass_sorcerer_approved = isClass_sorcerer_approved;
        this.isClass_monk_approved = isClass_monk_approved;
    }

    public ModConfig copy(){
        return new ModConfig(xp_per_lvl_multi, isInPounds, isRace_human_approved,
        isRace_dwarf_approved, isRace_elf_approved,
        isClass_fighter_approved, isClass_druid_approved,
        isClass_cleric_approved, isClass_wizard_approved,
        isClass_sorcerer_approved, isClass_monk_approved);
    }
}
