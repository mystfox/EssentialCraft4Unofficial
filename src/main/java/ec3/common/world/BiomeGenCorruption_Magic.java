package ec3.common.world;

import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenCorruption_Magic extends BiomeGenBase
{
    public BiomeGenCorruption_Magic(int par1)
    {
        super(par1);
        this.biomeName = "Corrupted Land";
    }
    
    public int getBiomeGrassColor()
    {
    	return 0xff00ff;
    }
    
    public int getBiomeFoliageColor()
    {
    	return 0xff00ff;
    }
    
    public int getWaterColorMultiplier()
    {
    	return 0xff00ff;
    }
    
    public int getModdedBiomeGrassColor(int original)
    {
    	return 0xff00ff;
    }
    
    public int getModdedBiomeFoliageColor(int original)
    {
    	return 0xff00ff;
    }
}
