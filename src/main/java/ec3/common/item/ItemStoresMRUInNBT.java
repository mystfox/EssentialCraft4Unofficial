package ec3.common.item;

import java.util.List;

import DummyCore.Utils.MiscUtils;
import ec3.api.IItemRequiresMRU;
import ec3.utils.common.ECUtils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemStoresMRUInNBT extends Item implements IItemRequiresMRU{
	int maxMRU = 5000;
	
	public ItemStoresMRUInNBT()
	{
	}
	
	public ItemStoresMRUInNBT setMaxMRU(int max)
	{
		maxMRU = max;
		return this;
	}
	
	@Override
	public boolean setMRU(ItemStack stack, int amount) {
		if(MiscUtils.getStackTag(stack).getInteger("mru")+amount >= 0 && MiscUtils.getStackTag(stack).getInteger("mru")+amount<=MiscUtils.getStackTag(stack).getInteger("maxMRU"))
		{
			MiscUtils.getStackTag(stack).setInteger("mru", MiscUtils.getStackTag(stack).getInteger("mru")+amount);
			return true;
		}
		return false;
	}
	
	@Override
	public int getMRU(ItemStack stack) {
		// TODO Auto-generated method stub
		return MiscUtils.getStackTag(stack).getInteger("mru");
	}

	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity entity, int indexInInventory, boolean isCurrentItem)
	{
		ECUtils.initMRUTag(itemStack, maxMRU);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) 
    {
    	par3List.add(ECUtils.getStackTag(par1ItemStack).getInteger("mru") + "/" + ECUtils.getStackTag(par1ItemStack).getInteger("maxMRU") + " MRU");
    }
    
	public void aFunc(ItemStack stack, World world, Entity entity, int par4, boolean par5)
	{
		
	}
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int var4 = 0; var4 < 1; ++var4)
        {
        	ItemStack min = new ItemStack(par1, 1, 0);
        	ECUtils.initMRUTag(min, maxMRU);
        	ItemStack max = new ItemStack(par1, 1, 0);
        	ECUtils.initMRUTag(max, maxMRU);
        	ECUtils.getStackTag(max).setInteger("mru", ECUtils.getStackTag(max).getInteger("maxMRU"));
            par3List.add(min);
            par3List.add(max);
        }
    }

	@Override
	public int getMaxMRU(ItemStack stack) {
		// TODO Auto-generated method stub
		return this.maxMRU;
	}
}
