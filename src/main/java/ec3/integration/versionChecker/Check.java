package ec3.integration.versionChecker;

import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.event.FMLInterModComms;

public class Check {
	
	public static void checkerCommit() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("curseProjectName", "254817-essentialcraft-4-unofficial");
		tag.setString("curseFilenameParser", "EssentialCraftv[].jar");
		FMLInterModComms.sendRuntimeMessage("essentialcraft", "VersionChecker","addCurseCheck", tag);
	}
}
