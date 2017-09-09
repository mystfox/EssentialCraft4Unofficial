package ec3.common.tile;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import ec3.api.ApiCore;
import ec3.utils.common.ECUtils;

public class TileMagicalRepairer extends TileMRUGeneric {
	
	public static float cfgMaxMRU = ApiCore.DEVICE_MAX_MRU_GENERIC;
	public static boolean generatesCorruption = true;
	public static int genCorruption = 3;
	public static int mruUsage = 70;
	
	public TileMagicalRepairer() {
		super();
		maxMRU = (int)cfgMaxMRU;
		setSlotsNum(2);
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		ECUtils.manage(this, 0);
		if(!worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
			repare();
		spawnParticles();
	}
	
	public void repare() {
		ItemStack repareItem = getStackInSlot(1);
		if(canRepare(repareItem)) {
			if(setMRU(getMRU() - mruUsage)) {
				repareItem.setItemDamage(repareItem.getItemDamage() - 1);
				if(generatesCorruption)
					ECUtils.increaseCorruptionAt(worldObj, xCoord, yCoord, zCoord, worldObj.rand.nextInt(genCorruption));
			}
		}
	}
	
	public boolean canRepare(ItemStack s) {
		return s != null && s.getItemDamage() != 0 && s.getItem().isRepairable() && getMRU() >= mruUsage;
	}
	
	public void spawnParticles() {
		if(canRepare(getStackInSlot(1)) && getMRU() > 0) {
			for(int o = 0; o < 10; ++o) {
				worldObj.spawnParticle("reddust", xCoord+0.25D + worldObj.rand.nextDouble()/2.2D, yCoord+0.25D+((float)o/20), zCoord+0.25D + worldObj.rand.nextDouble()/2.2D, 1.0D, 0.0D, 1.0D);
			}
		}
	}
	
	public static void setupConfig(Configuration cfg) {
		try {
			cfg.load();
			String[] cfgArrayString = cfg.getStringList("MagicalRepairerSettings", "tileentities", new String[] {
					"Max MRU:" + ApiCore.DEVICE_MAX_MRU_GENERIC,
					"MRU Usage:70",
					"Can this device actually generate corruption:true",
					"The amount of corruption generated each tick(do not set to 0!):3"
			}, "");
			String dataString = "";
			
			for(int i = 0; i < cfgArrayString.length; ++i)
				dataString += "||" + cfgArrayString[i];
			
			DummyData[] data = DataStorage.parseData(dataString);
			
			mruUsage = Integer.parseInt(data[1].fieldValue);
			cfgMaxMRU = Float.parseFloat(data[0].fieldValue);
			generatesCorruption = Boolean.parseBoolean(data[2].fieldValue);
			genCorruption = Integer.parseInt(data[3].fieldValue);
			
			cfg.save();
		}
		catch(Exception e) {
			return;
		}
	}
	
	@Override
	public int[] getOutputSlots() {
		return new int[] {1};
	}
}
