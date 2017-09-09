package ec3.common.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ec3.api.DemonTrade;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemCapturedSoul extends Item {

	public ItemCapturedSoul() {
		setHasSubtypes(true);
		setMaxDamage(0);
	}
	
	public String getUnlocalizedName(ItemStack stk) {
		return getUnlocalizedName() + "." + (stk.getItemDamage() < DemonTrade.allMobs.size() ? DemonTrade.allMobs.get(stk.getItemDamage()).getSimpleName() : "unknown");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itm, CreativeTabs tab, List lst) {
		for(int i = 0; i < DemonTrade.allMobs.size(); ++i) {
			lst.add(new ItemStack(itm,1,i));
		}
	}
}
