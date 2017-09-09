package ec3.common.tile;

import java.util.List;

import ec3.common.block.BlocksCore;
import ec3.common.registry.BiomeRegistry;
import ec3.common.registry.PotionRegistry;
import ec3.utils.common.ECUtils;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.MiscUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.ForgeDirection;

public class TileCorruption extends TileEntity {
	
	public static boolean canChangeBiome = true, canDestroyBlocks = true;
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		if(net.getNetHandler() instanceof INetHandlerPlayClient)
			if(pkt.func_148853_f() == -11) {
				NBTTagCompound packetTag = pkt.func_148857_g();
				int biomeID = packetTag.getInteger("biomeID");
				MiscUtils.changeBiome(worldObj, BiomeGenBase.getBiomeGenArray()[biomeID], xCoord, zCoord);
			}
	}
	
	public void changeBiomeAtPos(int biomeID) {
		if(canChangeBiome) {
			MiscUtils.changeBiome(worldObj, BiomeGenBase.getBiomeGenArray()[biomeID], xCoord, zCoord);
			NBTTagCompound nbttagcompound = new NBTTagCompound();
			nbttagcompound.setInteger("biomeID", biomeID);
			S35PacketUpdateTileEntity pkt = new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, -11, nbttagcompound);
			MiscUtils.sendPacketToAllAround(worldObj, pkt, xCoord, yCoord, zCoord, worldObj.provider.dimensionId, 32);
		}
	}
	
	public void addPotionEffectAtEntity(EntityLivingBase e, PotionEffect p) {
		if(!e.isPotionActive(p.getPotionID())) {
			e.addPotionEffect(p);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void updateEntity() {
		try {
			super.updateEntity();
			Potion potionToAdd = PotionRegistry.mruCorruptionPotion;
			Block blk = worldObj.getBlock(xCoord, yCoord, zCoord);
			if(blk == BlocksCore.lightCorruption[0]) {
				potionToAdd = PotionRegistry.chaosInfluence;
			}
			if(blk == BlocksCore.lightCorruption[1]) {
				potionToAdd = PotionRegistry.frozenMind;
			}
			List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1, yCoord+1, zCoord+1));
			for(int i = 0; i < players.size(); ++i) {
				if(!worldObj.isRemote) {
					float increasement = 5;
					increasement *= ECUtils.getGenResistance(1, players.get(i));
					ECUtils.calculateAndAddPE(players.get(i), potionToAdd, 2000, (int)increasement);
				}
			}
			if(worldObj.rand.nextFloat() <= 0.0001F) {
				int biomeId = 0;
				if(blk == BlocksCore.lightCorruption[0]) {
					biomeId = BiomeRegistry.chaosCorruption.biomeID;
				}
				if(blk == BlocksCore.lightCorruption[1]) {
					biomeId = BiomeRegistry.frozenCorruption.biomeID;
				}
				if(blk == BlocksCore.lightCorruption[2]) {
					biomeId = BiomeRegistry.shadowCorruption.biomeID;
				}
				if(blk == BlocksCore.lightCorruption[3]) {
					biomeId = BiomeRegistry.magicCorruption.biomeID;
				}
				if(!worldObj.isRemote)
					changeBiomeAtPos(biomeId);
			}
			int metadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			if(metadata >= 7 && canDestroyBlocks) {
				for(int i = 0; i < 6; ++i) {
					ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[i];
					if(worldObj.getTileEntity(xCoord+dir.offsetX, yCoord+dir.offsetY, zCoord+dir.offsetZ) == null && worldObj.getBlock(xCoord+dir.offsetX, yCoord+dir.offsetY, zCoord+dir.offsetZ).isBlockSolid(worldObj, xCoord+dir.offsetX, yCoord+dir.offsetY, zCoord+dir.offsetZ, dir.getOpposite().ordinal())) {
						worldObj.setBlock(xCoord+dir.offsetX, yCoord+dir.offsetY, zCoord+dir.offsetZ, blk, 0, 3);
					}
				}
				worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.air, 0, 3);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	public static void setupConfig(Configuration cfg) {
		try {
			cfg.load();
			String[] cfgArrayString = cfg.getStringList("CorruptionSettings", "tileentities", new String[]{
					"Change Biome:true",
					"Destroy Blocks if grown:true"
			}, "Settings of the given Device.");
			String dataString = "";
			
			for(int i = 0; i < cfgArrayString.length; ++i)
				dataString += "||" + cfgArrayString[i];
			
			DummyData[] data = DataStorage.parseData(dataString);
			canChangeBiome = Boolean.parseBoolean(data[0].fieldValue);
			canDestroyBlocks = Boolean.parseBoolean(data[1].fieldValue);
			cfg.save();
		}
		catch(Exception e) {
			return;
		}
	}
}
