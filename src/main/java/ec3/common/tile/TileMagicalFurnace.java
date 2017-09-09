package ec3.common.tile;

import java.util.ArrayList;
import java.util.List;

import ec3.api.ApiCore;
import ec3.common.block.BlocksCore;
import ec3.common.item.ItemsCore;
import ec3.common.mod.EssentialCraftCore;
import ec3.utils.common.ECUtils;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.MathUtils;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.config.Configuration;

public class TileMagicalFurnace extends TileMRUGeneric {
	public int progressLevel;
	public static float cfgMaxMRU = ApiCore.DEVICE_MAX_MRU_GENERIC*10;
	public static int mruUsage = 25;
	public static int smeltingTime = 20;
	public static float chanceToDoubleOutput = 0.3F;
	public static float chanceToDoubleSlags = 0.1F;
	
	public TileMagicalFurnace() {
		super();
		setSlotsNum(1);
	}
		
	@Override
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		progressLevel = par1NBTTagCompound.getInteger("progress");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("progress", progressLevel);
	}
	
	@Override
	public void updateEntity() {
		maxMRU = (int)cfgMaxMRU;
		super.updateEntity();
		spawnParticles();
		ECUtils.manage(this, 0);
		if(!worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
			smelt();
	}
    
	public boolean isStructureCorrect() {
		boolean flag = true;
		for(int x = -2; x <= 2; ++x) {
			for(int z = -2; z <= 2; ++z) {
				flag = worldObj.getBlock(xCoord+x, yCoord-1, zCoord+z) == BlocksCore.voidStone;
				if(!flag)
					return false;
			}
		}
		flag = 
				worldObj.getBlock(xCoord+2, yCoord, zCoord+2) == BlocksCore.voidStone &&
				worldObj.getBlock(xCoord-2, yCoord, zCoord+2) == BlocksCore.voidStone &&  
				worldObj.getBlock(xCoord+2, yCoord, zCoord-2) == BlocksCore.voidStone &&
				worldObj.getBlock(xCoord-2, yCoord, zCoord-2) == BlocksCore.voidStone &&
				worldObj.getBlock(xCoord+2, yCoord+1, zCoord+2) == BlocksCore.heatGenerator &&
				worldObj.getBlock(xCoord-2, yCoord+1, zCoord+2) == BlocksCore.heatGenerator &&  
				worldObj.getBlock(xCoord+2, yCoord+1, zCoord-2) == BlocksCore.heatGenerator &&
				worldObj.getBlock(xCoord-2, yCoord+1, zCoord-2) == BlocksCore.heatGenerator;
		return flag;
	}
	
	public void smelt() {
		ItemStack smeltingStack = getSmeltingStack();
		EntityItem smeltingItem = getSmeltingItem();
		if(isStructureCorrect() && getMRU() > 0 && smeltingStack != null && smeltingItem != null) {
			if(progressLevel == 0)
				worldObj.playSoundEffect(xCoord+0.5, yCoord+0.5, zCoord+0.5, "fire.fire", 1.0F, 1);
			ItemStack mainSmelting = smeltingStack.copy();
			++progressLevel;
			setMRU(getMRU() - mruUsage);
			if(progressLevel >= smeltingTime) {
				progressLevel = 0;
				worldObj.playSoundEffect(xCoord+0.5, yCoord+0.5, zCoord+0.5, "liquid.lavapop", 1.0F, 1);
				ItemStack s = FurnaceRecipes.smelting().getSmeltingResult(mainSmelting);
				s.stackSize += 1;
				if(!worldObj.isRemote) {
					--smeltingItem.getEntityItem().stackSize;
					if(smeltingItem.getEntityItem().stackSize <= 0) {
						smeltingItem.setPosition(0, 0, 0);
						smeltingItem.setDead();
					}
				}
				if(!worldObj.isRemote && worldObj.rand.nextFloat() <= chanceToDoubleOutput) {
					EntityItem smelted = new EntityItem(worldObj, xCoord-1.5, yCoord+2.15, zCoord-1.5, s.copy());
					worldObj.spawnEntityInWorld(smelted);
					smelted.motionX = 0;
					smelted.motionY = 0;
					smelted.motionZ = 0;
				}
				EntityItem smelted = new EntityItem(worldObj,xCoord+2.5, yCoord+2.15, zCoord+2.5, s.copy());
				if(!worldObj.isRemote)
					worldObj.spawnEntityInWorld(smelted);
				smelted.motionX = 0;
				smelted.motionY = 0;
				smelted.motionZ = 0;
				if(!worldObj.isRemote && worldObj.rand.nextFloat() <= chanceToDoubleSlags) {
					EntityItem slag = new EntityItem(worldObj,xCoord-1.5, yCoord+2.15, zCoord+2.5, new ItemStack(ItemsCore.magicalSlag));
					worldObj.spawnEntityInWorld(slag);
					smelted.motionX = 0;
					smelted.motionY = 0;
					smelted.motionZ = 0;
				}
				EntityItem slag = new EntityItem(worldObj,xCoord+2.5,yCoord+2.15,zCoord-1.5,new ItemStack(ItemsCore.magicalSlag));
				if(!worldObj.isRemote)
					worldObj.spawnEntityInWorld(slag);
				smelted.motionX = 0;
				smelted.motionY = 0;
				smelted.motionZ = 0;
				s.stackSize = 0;
				s = null;
			}
		}
		else
			progressLevel = 0;
	}
	
	@SuppressWarnings("unchecked")
	public EntityItem getSmeltingItem() {
		EntityItem ret = null;
		List<EntityItem> l = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1, yCoord+2, zCoord+1));
		if(!l.isEmpty()) {
			for(int i = 0; i < l.size(); ++i) {
				EntityItem item = l.get(i);
				if(FurnaceRecipes.smelting().getSmeltingResult(item.getEntityItem()) != null)
					return item;
			}
		}
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public ItemStack getSmeltingStack() {
		ItemStack ret = null;
		List<EntityItem> l = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1, yCoord+2, zCoord+1));
		if(!l.isEmpty()) {
			List<ItemStack> canBeSmelted = new ArrayList<ItemStack>();
			for(int i = 0; i < l.size(); ++i) {
				EntityItem item = l.get(i);
				if(FurnaceRecipes.smelting().getSmeltingResult(item.getEntityItem()) != null)
					canBeSmelted.add(item.getEntityItem());
			}
			if(!canBeSmelted.isEmpty())
				ret = canBeSmelted.get(0);
		}
		return ret;
	}
	
	public void spawnParticles() {
		if(isStructureCorrect()) {
			/*for(int i = 0; i < 100; ++i) */ {
				EssentialCraftCore.proxy.spawnParticle("cSpellFX", xCoord+0.5F + MathUtils.randomFloat(worldObj.rand)*3, yCoord, zCoord+0.5F + MathUtils.randomFloat(worldObj.rand)*3, 0,2, 0);
			}
		}
	}
	
	public static void setupConfig(Configuration cfg) {
		try {
			cfg.load();
			String[] cfgArrayString = cfg.getStringList("MagicalFurnaceSettings", "tileentities", new String[] {
					"Max MRU:" + ApiCore.DEVICE_MAX_MRU_GENERIC*10,
					"MRU Usage:25",
					"Ticks required to smelt 1 item:20",
					"Chance to double the outcome:0.3",
					"Chance to double slags outcome:0.1"
			}, "");
			String dataString = "";
			
			for(int i = 0; i < cfgArrayString.length; ++i)
				dataString += "||" + cfgArrayString[i];
			
			DummyData[] data = DataStorage.parseData(dataString);
			
			mruUsage = Integer.parseInt(data[1].fieldValue);
			smeltingTime = Integer.parseInt(data[2].fieldValue);
			cfgMaxMRU = Float.parseFloat(data[0].fieldValue);
			chanceToDoubleOutput = Float.parseFloat(data[3].fieldValue);
			chanceToDoubleSlags = Float.parseFloat(data[4].fieldValue);
			
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
