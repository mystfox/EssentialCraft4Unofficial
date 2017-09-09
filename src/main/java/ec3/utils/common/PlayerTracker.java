package ec3.utils.common;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import baubles.api.BaublesApi;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import ec3.api.ApiCore;
import ec3.api.IWindResistance;
import ec3.common.item.ItemComputerArmor;
import ec3.common.item.ItemGenericArmor;
import ec3.common.item.ItemsCore;
import ec3.common.registry.PotionRegistry;

public class PlayerTracker{
	
	@SubscribeEvent
	public void onPlayerLogOn(PlayerEvent event)
	{
	}
	
	@SubscribeEvent
	public void onDescrAdded(ItemTooltipEvent event)
	{
		ItemStack stk = event.itemStack;
		Item i = stk.getItem();
		if(ApiCore.reductionsTable.containsKey(i))
		{
			ArrayList<Float> fltLst = ApiCore.reductionsTable.get(i);
			event.toolTip.add(EnumChatFormatting.GOLD+"+"+((int)(fltLst.get(0)*100))+"% "+EnumChatFormatting.DARK_PURPLE+"to MRUCorruption resistance");
			event.toolTip.add(EnumChatFormatting.GOLD+"+"+((int)(fltLst.get(1)*100))+"% "+EnumChatFormatting.DARK_PURPLE+"to MRURadiation resistance");
			event.toolTip.add(EnumChatFormatting.GOLD+"-"+((int)(fltLst.get(2)*100))+"% "+EnumChatFormatting.DARK_PURPLE+"to Corruption affection");
			
		}
	}
	
	@SubscribeEvent
	public void onPlayerJump(LivingJumpEvent event)
	{
		if(event.entityLiving instanceof EntityPlayer && !event.entityLiving.worldObj.isRemote && event.entityLiving.worldObj.rand.nextFloat() < 0.0003F)
		{
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			boolean addBuff = true;
			if(ECUtils.getData(player).isWindbound())
			{
				if(BaublesApi.getBaubles(player) != null)
				{
					for(int i = 0; i < BaublesApi.getBaubles(player).getSizeInventory(); ++i)
					{
						if(BaublesApi.getBaubles(player).getStackInSlot(i) != null)
						{
							if(BaublesApi.getBaubles(player).getStackInSlot(i).getItem() != null)
							{
								if(BaublesApi.getBaubles(player).getStackInSlot(i).getItem() instanceof IWindResistance)
								{
									if(addBuff)
										addBuff = !((IWindResistance)BaublesApi.getBaubles(player).getStackInSlot(i).getItem()).resistWind(player, BaublesApi.getBaubles(player).getStackInSlot(i));
								}
							}
						}
					}
				}
				
				if(player.inventory != null)
				{
					for(int i = 0; i < player.inventory.armorInventory.length; ++i)
					{
						if(player.inventory.armorInventory[i] != null)
						{
							if(player.inventory.armorInventory[i].getItem() != null)
							{
								if(player.inventory.armorInventory[i].getItem() instanceof IWindResistance)
								{
									if(addBuff)
										addBuff = !((IWindResistance)player.inventory.armorInventory[i].getItem()).resistWind(player, BaublesApi.getBaubles(player).getStackInSlot(i));
								}
							}
						}
					}
				}
				
				if(addBuff)
					event.entityLiving.addPotionEffect(new PotionEffect(Potion.jump.id,100,12));
				player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA+""+EnumChatFormatting.ITALIC+"The wind pushes you upwards..."));
				
				WindRelations.increasePlayerWindRelations(player, 1000);
			}

		}
		if(event.entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			if(player.inventory.armorInventory[1] != null && player.inventory.armorInventory[1].getItem() instanceof ItemGenericArmor)
			{
				player.motionY += 0.2D;
			}
			if(player.inventory.armorInventory[1] != null && player.inventory.armorInventory[1].getItem() instanceof ItemComputerArmor)
			{
				player.motionY += 0.3D;
			}
		}
		
	}
	
	@SubscribeEvent
	public void onPlayerHurt(LivingHurtEvent event)
	{
		if(event.entityLiving instanceof EntityPlayer && !event.entityLiving.worldObj.isRemote)
		{
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			ItemStack chestplate = player.inventory.armorInventory[2];
			if(chestplate != null && chestplate.getItem() == ItemsCore.magicArmorItems[5] && event.entityLiving.worldObj.rand.nextFloat() <= 0.2F)
			{
				event.ammount = 0F;
				for(int i = 0; i < 100; ++i)
				{
					MiscUtils.spawnParticlesOnServer("reddust", (float)(player.posX+MathUtils.randomDouble(event.entityLiving.worldObj.rand)/2), (float)(player.posY+1+MathUtils.randomDouble(event.entityLiving.worldObj.rand)), (float)(player.posZ+MathUtils.randomDouble(event.entityLiving.worldObj.rand)/2), -1, 0, 0);
				}
				return;
			}
			if(chestplate != null && chestplate.getItem() == ItemsCore.computer_chestplate)
			{
				if(event.source != null && (event.source == DamageSource.wither || event.source == DamageSource.magic || event.source == DamageSource.starve))
					event.setCanceled(true);
				return;
			}
			ItemStack boots = player.inventory.armorInventory[0];
			if(boots != null && boots.getItem() == ItemsCore.magicArmorItems[7])
			{
				if(event.source == DamageSource.fall)
				{
					event.ammount -= event.ammount*0.9F;
				}
			}
			if(boots != null && boots.getItem() == ItemsCore.computer_boots)
			{
				if(event.source == DamageSource.fall)
				{
					event.ammount = 0;
					return;
				}
			}
			if(player.getActivePotionEffect(PotionRegistry.chaosInfluence) != null)
			{
				int amplifier = player.getActivePotionEffect(PotionRegistry.chaosInfluence).getAmplifier();
				event.ammount *= 1+amplifier;
			}
			if(player.getActivePotionEffect(PotionRegistry.frozenMind) != null)
			{
				ECUtils.calculateAndAddPE(player, Potion.moveSlowdown, 400,100);
				ECUtils.calculateAndAddPE(player, Potion.weakness, 400,100);
				ECUtils.calculateAndAddPE(player, Potion.digSlowdown, 400,100);
			}
		}
	}

}
