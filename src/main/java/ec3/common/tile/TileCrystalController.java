package ec3.common.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.config.Configuration;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import ec3.api.ApiCore;
import ec3.common.item.ItemEssence;
import ec3.utils.common.ECUtils;

public class TileCrystalController extends TileMRUGeneric {
	
	public static float cfgMaxMRU = ApiCore.DEVICE_MAX_MRU_GENERIC;
	public static int mruUsage = 100;
	public static int chanceToUseMRU = 20;
	public static float mutateModifier = 0.001F;
	
	public TileCrystalController() {
		super();
		maxMRU = (int)cfgMaxMRU;
		setSlotsNum(2);
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		ECUtils.manage(this, 0);
		
		if(!worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
			if(!worldObj.isRemote && worldObj.rand.nextInt(chanceToUseMRU) == 0 && getMRU() >= mruUsage) {
				setMRU(getMRU() - mruUsage);
			}
		spawnParticles();
		if(!worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
			mutateToElement();
	}
	
	public void spawnParticles() {
		if(getMRU() > 0 && getCrystal() != null) {
			for(int o = 0; o < 2; ++o) {
				worldObj.spawnParticle("reddust", xCoord+0.3D + worldObj.rand.nextDouble()/2, yCoord+0.3F + ((float)o/2), zCoord+0.3D + worldObj.rand.nextDouble()/2D, -1.0D, 1.0D, 0.0D);
			}
		}
	}
	
	public void mutateToElement() {
		if(getStackInSlot(1) != null && getStackInSlot(1).getItem() instanceof ItemEssence && getMRU() > 500 && !worldObj.isRemote && getCrystal() != null && getCrystal().size < 100) {
			ItemStack e = getStackInSlot(1);
			TileElementalCrystal c = getCrystal();
			int rarity = (int)((float)e.getItemDamage() / 4);
			float chance = (float) (mutateModifier * (rarity + 1));
			if(worldObj.rand.nextFloat() <= chance) {
				int type = ItemEssence.convertDamageToIntBefore4(e.getItemDamage());
				c.mutate(type, worldObj.rand.nextInt((rarity + 1) * 2));
				decrStackSize(1, 1);
				setMRU(getMRU() - mruUsage*10);
			}
		}
	}
	
	public TileElementalCrystal getCrystal() {
		TileElementalCrystal t = null;
		if(hasCrystalOnFront())
			t = (TileElementalCrystal)worldObj.getTileEntity(xCoord+1, yCoord, zCoord);
		if(hasCrystalOnBack())
			t = (TileElementalCrystal)worldObj.getTileEntity(xCoord-1, yCoord, zCoord);
		if(hasCrystalOnLeft())
			t = (TileElementalCrystal)worldObj.getTileEntity(xCoord, yCoord, zCoord+1);
		if(hasCrystalOnRight())
			t = (TileElementalCrystal)worldObj.getTileEntity(xCoord, yCoord, zCoord-1);
		return t;
	}
	
	public boolean hasCrystalOnFront() {
		TileEntity t = worldObj.getTileEntity(xCoord + 1, yCoord, zCoord);
		return t != null && t instanceof TileElementalCrystal;
	}
	
	public boolean hasCrystalOnBack() {
		TileEntity t = worldObj.getTileEntity(xCoord - 1, yCoord, zCoord);
		return t != null && t instanceof TileElementalCrystal;
	}
	
	public boolean hasCrystalOnLeft() {
		TileEntity t = worldObj.getTileEntity(xCoord, yCoord, zCoord + 1);
		return t != null && t instanceof TileElementalCrystal;
	}
	
	public boolean hasCrystalOnRight() {
		TileEntity t = worldObj.getTileEntity(xCoord, yCoord, zCoord - 1);
		return t != null && t instanceof TileElementalCrystal;
	}
	
	public static void setupConfig(Configuration cfg) {
		try {
			cfg.load();
			String[] cfgArrayString = cfg.getStringList("CrystalControllerSettings", "tileentities", new String[] {
					"Max MRU:" + ApiCore.DEVICE_MAX_MRU_GENERIC,
					"MRU Usage:100",
					"Chance to NOT use MRU(do not set to 0!):20",
					"Crystal mutation chance modifier:0.001"
			}, "");
			String dataString="";
			
			for(int i = 0; i < cfgArrayString.length; ++i)
				dataString += "||" + cfgArrayString[i];
			
			DummyData[] data = DataStorage.parseData(dataString);
			
			mutateModifier = Float.parseFloat(data[3].fieldValue);
			mruUsage = Integer.parseInt(data[1].fieldValue);
			chanceToUseMRU = Integer.parseInt(data[2].fieldValue);
			cfgMaxMRU = Float.parseFloat(data[0].fieldValue);
			
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
