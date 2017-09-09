package ec3.common.tile;

import java.util.List;

import cpw.mods.fml.common.Loader;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.config.Configuration;
import ec3.api.ApiCore;

public class TileEnderGenerator extends TileMRUGeneric {
	
	public static float cfgMaxMRU = ApiCore.GENERATOR_MAX_MRU_GENERIC;
	public static float cfgBalance = -1F;
	public static float mruGenerated = 500;
	public static int endermenCatchRadius = 8;
	
	public TileEnderGenerator() {
		super();
		maxMRU = (int)cfgMaxMRU;
		balance = cfgBalance;
		slot0IsBoundGem = false;
	}
	
	public boolean canGenerateMRU() {
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void updateEntity() {
		super.updateEntity();
		if(balance == -1)
			balance = worldObj.rand.nextFloat()*2;
		if(!worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
			AxisAlignedBB endermenTPRadius = AxisAlignedBB.getBoundingBox(xCoord-endermenCatchRadius, yCoord-endermenCatchRadius, zCoord-endermenCatchRadius, xCoord+endermenCatchRadius+1, yCoord+endermenCatchRadius+1, zCoord+endermenCatchRadius+1);
			List<EntityEnderman> l = worldObj.getEntitiesWithinAABB(EntityEnderman.class, endermenTPRadius);
			if(Loader.isModLoaded("HardcoreEnderExpansion")) {
				try {
					l.addAll(worldObj.getEntitiesWithinAABB(Class.forName("chylex.hee.entity.mob.EntityMobAngryEnderman"), endermenTPRadius));
					l.addAll(worldObj.getEntitiesWithinAABB(Class.forName("chylex.hee.entity.mob.EntityMobBabyEnderman"), endermenTPRadius));
					l.addAll(worldObj.getEntitiesWithinAABB(Class.forName("chylex.hee.entity.mob.EntityMobEndermage"), endermenTPRadius));
					l.addAll(worldObj.getEntitiesWithinAABB(Class.forName("chylex.hee.entity.mob.EntityMobEnderman"), endermenTPRadius));
					l.addAll(worldObj.getEntitiesWithinAABB(Class.forName("chylex.hee.entity.mob.EntityMobParalyzedEnderman"), endermenTPRadius));
				}
				catch(ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			if(!l.isEmpty()) {
				for(int i = 0; i < l.size(); ++i) {
					l.get(i).setPositionAndRotation(xCoord+0.5D, yCoord+1D, zCoord+0.5D, 0, 0);
				}
			}
			AxisAlignedBB endermanAttackRad = AxisAlignedBB.getBoundingBox(xCoord-2, yCoord-2, zCoord-2, xCoord+2, yCoord+2, zCoord+2);
			List<EntityEnderman> l1 = worldObj.getEntitiesWithinAABB(EntityEnderman.class, endermanAttackRad);
			if(Loader.isModLoaded("HardcoreEnderExpansion")) {
				try {
					l1.addAll(worldObj.getEntitiesWithinAABB(Class.forName("chylex.hee.entity.mob.EntityMobAngryEnderman"), endermanAttackRad));
					l1.addAll(worldObj.getEntitiesWithinAABB(Class.forName("chylex.hee.entity.mob.EntityMobBabyEnderman"), endermanAttackRad));
					l1.addAll(worldObj.getEntitiesWithinAABB(Class.forName("chylex.hee.entity.mob.EntityMobEndermage"), endermanAttackRad));
					l1.addAll(worldObj.getEntitiesWithinAABB(Class.forName("chylex.hee.entity.mob.EntityMobEnderman"), endermanAttackRad));
					l1.addAll(worldObj.getEntitiesWithinAABB(Class.forName("chylex.hee.entity.mob.EntityMobParalyzedEnderman"), endermanAttackRad));
				}
				catch(ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			if(!l1.isEmpty()) {
				if(!worldObj.isRemote) {
					for(int i = 0; i < l1.size(); ++i) {
						if(l1.get(i).attackEntityFrom(DamageSource.magic, 1)) {
							setMRU((int)(getMRU() + mruGenerated));
							if(getMRU() > getMaxMRU())
								setMRU(getMaxMRU());
						}
					}
				}
			}
		}
	}
	
	public static void setupConfig(Configuration cfg) {
		try {
			cfg.load();
			String[] cfgArrayString = cfg.getStringList("EnderGeneratorSettings", "tileentities", new String[] {
					"Max MRU:" + ApiCore.GENERATOR_MAX_MRU_GENERIC,
					"Default balance(-1 is random):-1.0",
					"MRU generated per hit:500",
					"Radius of Endermen detection:8"
			}, "");
			String dataString = "";
			
			for(int i = 0; i < cfgArrayString.length; ++i)
				dataString += "||" + cfgArrayString[i];
			
			DummyData[] data = DataStorage.parseData(dataString);
			
			cfgMaxMRU = Float.parseFloat(data[0].fieldValue);
			cfgBalance = Float.parseFloat(data[1].fieldValue);
			mruGenerated = Float.parseFloat(data[2].fieldValue);
			endermenCatchRadius = Integer.parseInt(data[3].fieldValue);
			
			cfg.save();
		}
		catch(Exception e) {
			return;
		}
	}
	
	@Override
	public int[] getOutputSlots() {
		return new int[0];
	}
}
