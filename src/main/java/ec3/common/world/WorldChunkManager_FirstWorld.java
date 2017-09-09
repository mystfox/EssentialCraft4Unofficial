package ec3.common.world;

import ec3.common.registry.BiomeRegistry;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;

public class WorldChunkManager_FirstWorld extends WorldChunkManager{
	
    public WorldChunkManager_FirstWorld(long p_i1975_1_, WorldType p_i1975_3_)
    {
    	super(p_i1975_1_,p_i1975_3_);
    }

    public WorldChunkManager_FirstWorld(World p_i1976_1_)
    {
    	super(p_i1976_1_);
    }

    public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] p_76931_1_, int p_76931_2_, int p_76931_3_, int p_76931_4_, int p_76931_5_, boolean p_76931_6_)
    {
    	BiomeGenBase[] bgb = super.getBiomeGenAt(p_76931_1_, p_76931_2_, p_76931_3_, p_76931_4_, p_76931_5_, p_76931_6_);
    	for(int i = 0; i < bgb.length; ++i)
    	{
    		BiomeGenBase biome = bgb[i];
    		if(biome != null)
    		{
	    		int id = biome.biomeID;
	    		int newId = id%BiomeRegistry.firstWorldBiomeArray.length;
	    		bgb[i] = BiomeRegistry.firstWorldBiomeArray[newId];
    		}
    	}
    	
    	return bgb;
    }
}
