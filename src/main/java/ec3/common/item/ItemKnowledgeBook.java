package ec3.common.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import DummyCore.Utils.MiscUtils;
import ec3.common.mod.EssentialCraftCore;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemKnowledgeBook extends Item {

	public IIcon icon;
	public ItemKnowledgeBook() {
		super();
		this.maxStackSize = 1;
	}

    
	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		EssentialCraftCore.proxy.openBookGUIForPlayer();
        return par1ItemStack;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) 
    {
    	NBTTagCompound theTag = MiscUtils.getStackTag(par1ItemStack);
    	par3List.add("\u00a76" + StatCollector.translateToLocal("ec3.txt.book.containedKnowledge"));
		 int tier = theTag.getInteger("tier");
		 for(int i = 0; i <= tier; ++i)
		 {
			 par3List.add("\u00a77-\u00a7o" + StatCollector.translateToLocal("ec3.txt.book.tier_"+i));
		 }
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
    {
    	for(int i = 0; i < 5; ++i)
    	{
    		ItemStack book = new ItemStack(p_150895_1_);
    		NBTTagCompound bookTag = new NBTTagCompound();
    		bookTag.setInteger("tier", i);
    		book.setTagCompound(bookTag);
    		p_150895_3_.add(book);
    	}
    }
    
    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
    	super.registerIcons(par1IconRegister);
        this.icon = par1IconRegister.registerIcon("essentialcraft:book_knowledge");
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack i)
    {
        return this.icon;
    }
}
