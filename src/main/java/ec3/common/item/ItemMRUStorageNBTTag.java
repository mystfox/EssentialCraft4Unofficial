package ec3.common.item;

import java.util.List;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import DummyCore.Utils.MathUtils;
import ec3.api.IItemRequiresMRU;
import ec3.api.IMRUStorage;
import ec3.utils.common.ECUtils;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemMRUStorageNBTTag extends Item implements IMRUStorage, IItemRequiresMRU, IBauble {
	int[] maxMRU = new int[6];
	IIcon[] itemIcon = new IIcon[6];
	int[] colors = new int[] {0x555555, 0x665555, 0x775555, 0x885555, 0x995555, 0xaa5555, 0xbb5555, 0xcc5555, 0xdd5555, 0xee6666, 0xff6666};
	public static String[] dropNames = new String[] {"SoulShard", "SoulSphere", "DarkSoulMatter", "RedSoulMatter", "MatterOfEthernity", "Unknown"};
	public ItemMRUStorageNBTTag(int[] maxMRU) {
		super();
		this.maxMRU = maxMRU;
		setMaxStackSize(1);
	}

	@Override
	public boolean setMRU(ItemStack stack, int amount) {
		if(ECUtils.getStackTag(stack).getInteger("mru")+amount >= 0 && ECUtils.getStackTag(stack).getInteger("mru")+amount<=ECUtils.getStackTag(stack).getInteger("maxMRU")) {
			ECUtils.getStackTag(stack).setInteger("mru", ECUtils.getStackTag(stack).getInteger("mru")+amount);
			return true;
		}
		return false;
	}

	@Override
	public int getMRU(ItemStack stack) {
		return ECUtils.getStackTag(stack).getInteger("mru");
	}
	
	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity entity, int indexInInventory, boolean isCurrentItem) {
		ECUtils.initMRUTag(itemStack, maxMRU[itemStack.getItemDamage()]);
	}
	
    @SuppressWarnings({"unchecked", "rawtypes"})
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
    	par3List.add(ECUtils.getStackTag(par1ItemStack).getInteger("mru") + "/" + ECUtils.getStackTag(par1ItemStack).getInteger("maxMRU") + " MRU");
    }
    
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        for(int var4 = 0; var4 < 5; ++var4) {
        	ItemStack min = new ItemStack(par1, 1, var4);
        	ECUtils.initMRUTag(min, getMaxMRU(min));
        	ItemStack max = new ItemStack(par1, 1, var4);
        	ECUtils.initMRUTag(max, getMaxMRU(max));
        	ECUtils.getStackTag(max).setInteger("mru", ECUtils.getStackTag(max).getInteger("maxMRU"));
            par3List.add(min);
            par3List.add(max);
        }
    }
    
    public IIcon getIconFromDamage(int par1) {
    	return itemIcon[Math.min(par1, itemIcon.length-1)];
    }
    
    public String getUnlocalizedName(ItemStack p_77667_1_) {
        return getUnlocalizedName()+dropNames[Math.min(p_77667_1_.getItemDamage(), dropNames.length-1)];
    }
    
    @Override
    public void registerIcons(IIconRegister par1IconRegister) {
        itemIcon[0] = par1IconRegister.registerIcon("essentialcraft:storages/soulShard");
        itemIcon[1] = par1IconRegister.registerIcon("essentialcraft:storages/soulSphere");
        itemIcon[2] = par1IconRegister.registerIcon("essentialcraft:storages/darkSoulMatter");
        itemIcon[3] = par1IconRegister.registerIcon("essentialcraft:storages/redSoulMatter");
        itemIcon[4] = par1IconRegister.registerIcon("essentialcraft:storages/eternityMatter");
    }
    
	public int getMaxMRU(ItemStack stack) {
		int dam = stack.getItemDamage();
		return maxMRU[dam];
	}
	
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
    	int dam = par1ItemStack.getItemDamage();
    	if(dam != -1) {
    		int currentMRU = ((IMRUStorage)par1ItemStack.getItem()).getMRU(par1ItemStack);
    		int maxMRU = ((IMRUStorage)par1ItemStack.getItem()).getMaxMRU(par1ItemStack);
    		int percentage = MathUtils.getPercentage(currentMRU, maxMRU);
    		percentage /= 10;
    		return colors[percentage];
    	}
        return 16777215;
    }
    
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.RING;
	}
	
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {}
	
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {}
	
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {}
	
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}
	
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}
}
