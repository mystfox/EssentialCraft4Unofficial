package ec3.common.block;

import DummyCore.Utils.MiscUtils;
import ec3.common.mod.EssentialCraftCore;
import ec3.common.tile.TileecAcceptor;
import ec3.utils.cfg.Config;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockecAcceptor extends BlockContainer{

	protected BlockecAcceptor(Material p_i45386_1_) {
		super(p_i45386_1_);
		// TODO Auto-generated constructor stub
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		// TODO Auto-generated method stub
		return new TileecAcceptor();
	}

    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        if (par1World.isRemote)
        {
            return true;
        }else
        {
        	if(!par5EntityPlayer.isSneaking())
        	{
        		par5EntityPlayer.openGui(EssentialCraftCore.core, Config.guiID[0], par1World, par2, par3, par4);
            	return true;
        	}else
        	{
        		return false;
        	}
        }
    }
    
	@Override
    public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6)
    {
    	MiscUtils.dropItemsOnBlockBreak(par1World, par2, par3, par4, par5, par6);
    	super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
}
