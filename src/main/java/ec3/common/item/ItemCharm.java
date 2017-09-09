package ec3.common.item;

import java.util.List;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import ec3.utils.common.ECUtils;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;

public class ItemCharm extends ItemStoresMRUInNBT implements IBauble {

	public IIcon[] icon = new IIcon[10];
	public String[] name = new String[] {"Fire", "Water", "Earth", "Air", "Steam", "Magma", "Lightning", "Life", "Rain", "Dust", "None"};
	public ItemCharm() {
		super();
		setMaxMRU(5000);
		setMaxDamage(0);
		maxStackSize = 1;
		bFull3D = false;
		setHasSubtypes(true);
	}
	
	public void onWornTick(ItemStack s, EntityLivingBase entity) {
		if(entity instanceof EntityPlayer) {
			EntityPlayer e = (EntityPlayer) entity;
			int dam = s.getItemDamage();
			switch(dam) {
			case 0:
				updateFire(e, s);
				break;
			case 1:
				updateWater(e, s);
				break;
			case 2:
				updateEarth(e, s);
				break;
			case 3:
				updateAir(e, s);
				break;
			case 4:
				updateSteam(e, s);
				break;
			case 5:
				updateMagma(e, s);
				break;
			case 6:
				updateLightning(e, s);
				break;
			case 7:
				updateLife(e, s);
				break;
			case 8:
				updateRain(e, s);
				break;
			case 9:
				updateDust(e, s);
				break;
			}
		}
	}
	
	public IIcon getIconFromDamage(int par1) {
		return icon[Math.min(par1, icon.length-1)];
	}
	
	public void registerIcons(IIconRegister par1IconRegister) {
		for(int i = 0; i < 10; ++i) {
			icon[i] = par1IconRegister.registerIcon("essentialcraft:tools/charm"+name[i]);
		}
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(int var4 = 0; var4 < 10; ++var4) {
			ItemStack min = new ItemStack(par1, 1, var4);
			ECUtils.initMRUTag(min, maxMRU);
			ItemStack max = new ItemStack(par1, 1, var4);
			ECUtils.initMRUTag(max, maxMRU);
			ECUtils.getStackTag(max).setInteger("mru", ECUtils.getStackTag(max).getInteger("maxMRU"));
			par3List.add(min);
			par3List.add(max);
		}
	}
	
	public void updateFire(EntityPlayer e, ItemStack s) {
		if(e.isBurning() && !e.isPotionActive(Potion.fireResistance.id) && (ECUtils.tryToDecreaseMRUInStorage(e, -50) || setMRU(s, -50))) {
			e.extinguish();
			e.worldObj.playSoundAtEntity(e, "random.fizz", 1, 1);
		}
	}
	
	public void updateWater(EntityPlayer e, ItemStack s) {
		if(e.getAir() < 10 && e.isInWater() && (ECUtils.tryToDecreaseMRUInStorage(e, -100) || setMRU(s, -100))) {
			e.setAir(100);
			e.worldObj.playSoundAtEntity(e, "random.breath", 1, 1);
		}
	}
	
	public void updateEarth(EntityPlayer e, ItemStack s) {
		if(e.hurtTime > 0 && !e.isPotionActive(Potion.resistance.id) && (ECUtils.tryToDecreaseMRUInStorage(e, -200) || setMRU(s, -200)))
			e.addPotionEffect(new PotionEffect(Potion.resistance.id, 100, 0));
	}
	
	public void updateAir(EntityPlayer e, ItemStack s) {
		if(e.isSprinting() && !e.isPotionActive(Potion.moveSpeed.id) && (ECUtils.tryToDecreaseMRUInStorage(e, -10) || setMRU(s, -10))) {
			e.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 20, 1));
			e.worldObj.playSoundAtEntity(e, "random.breath", 1, .01F);
		}
	}
	
	public void updateSteam(EntityPlayer e, ItemStack s) {
		if(e.getHealth() < 5 && !e.isPotionActive(Potion.moveSpeed.id) && (ECUtils.tryToDecreaseMRUInStorage(e, -200) || setMRU(s, -200))) {
			e.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 100, 5));
			e.worldObj.playSoundAtEntity(e, "random.breath", 1, .01F);
			e.worldObj.playSoundAtEntity(e, "random.fizz", 1, .01F);
		}
	}
	
	public void updateMagma(EntityPlayer e, ItemStack s) {
		Material m = e.worldObj.getBlock((int)e.posX-1, (int)e.posY-1, (int)e.posZ).getMaterial();
		Material m1 = e.worldObj.getBlock((int)e.posX-1, (int)e.posY, (int)e.posZ).getMaterial();
		if((m == Material.lava || m1 == Material.lava) && !e.isPotionActive(Potion.fireResistance.id) && (ECUtils.tryToDecreaseMRUInStorage(e, -100) || setMRU(s, -100))) {
			e.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 100, 0));
			e.worldObj.playSoundAtEntity(e, "liquid.lava", 1, 10F);
		}
	}
	
	public void updateLightning(EntityPlayer e, ItemStack s) {
		if(e.worldObj.isThundering()&& !e.isPotionActive(Potion.damageBoost.id) && (ECUtils.tryToDecreaseMRUInStorage(e, -100) || setMRU(s, -100))) {
			e.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 100, 0));
			e.addPotionEffect(new PotionEffect(Potion.nightVision.id, 600, 0));
			e.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 100, 0));
			e.worldObj.playSoundAtEntity(e, "ambient.weather.thunder1", 1, 1F);
		}
	}
	
	public void updateLife(EntityPlayer e, ItemStack s) {
		if(e.getHealth() < 5 && !e.isPotionActive(Potion.regeneration.id) && (ECUtils.tryToDecreaseMRUInStorage(e, -200) || setMRU(s, -200))) {
			e.addPotionEffect(new PotionEffect(Potion.regeneration.id, 50, 1));
			e.worldObj.playSoundAtEntity(e, "random.orb", 1,  10F);
		}
		if(e.getHealth() < 20 && !e.isPotionActive(Potion.regeneration.id) && (ECUtils.tryToDecreaseMRUInStorage(e, -50) || setMRU(s, -50))) {
			e.addPotionEffect(new PotionEffect(Potion.regeneration.id, 50, 0));
			e.worldObj.playSoundAtEntity(e, "random.orb", 1, 10F);
		}
	}
	
	public void updateRain(EntityPlayer e, ItemStack s) {
		if(e.worldObj.isRaining() && !e.isPotionActive(Potion.digSpeed.id) && (ECUtils.tryToDecreaseMRUInStorage(e, -50) || setMRU(s, -50))) {
			e.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 100, 0));
			e.addPotionEffect(new PotionEffect(Potion.jump.id, 100, 2));
			e.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 100, 0));
			e.worldObj.playSoundAtEntity(e, "liquid.splash", 1, 1F);
		}
	}
	
	public void updateDust(EntityPlayer e, ItemStack s) {
		Material m = e.worldObj.getBlock((int)e.posX-1, (int)e.posY-1, (int)e.posZ).getMaterial();
		if(m == Material.sand && !e.isPotionActive(Potion.resistance.id) && (ECUtils.tryToDecreaseMRUInStorage(e, -100) || setMRU(s, -100))) {
			e.addPotionEffect(new PotionEffect(Potion.resistance.id, 100, 0));
			e.addPotionEffect(new PotionEffect(Potion.regeneration.id, 100, 0));
		}
	}
	
	public String getItemDisplayName(ItemStack par1ItemStack) {
		return "Charm Of "+name[Math.min(par1ItemStack.getItemDamage(), name.length-1)];
	}
	
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.AMULET;
	}
	
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {}
	
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {}
	
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}
	
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}
}
