package ec3.common.registry;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import ec3.common.tile.TileAMINEjector;
import ec3.common.tile.TileAMINInjector;
import ec3.common.tile.TileAdvancedBlockBreaker;
import ec3.common.tile.TileAnimalSeparator;
import ec3.common.tile.TileChargingChamber;
import ec3.common.tile.TileColdDistillator;
import ec3.common.tile.TileCorruption;
import ec3.common.tile.TileCorruptionCleaner;
import ec3.common.tile.TileCrafter;
import ec3.common.tile.TileCreativeMRUSource;
import ec3.common.tile.TileCrystalController;
import ec3.common.tile.TileCrystalExtractor;
import ec3.common.tile.TileCrystalFormer;
import ec3.common.tile.TileDarknessObelisk;
import ec3.common.tile.TileDemonicPentacle;
import ec3.common.tile.TileElementalCrystal;
import ec3.common.tile.TileEmberForge;
import ec3.common.tile.TileEnderGenerator;
import ec3.common.tile.TileFlowerBurner;
import ec3.common.tile.TileFurnaceMagic;
import ec3.common.tile.TileHeatGenerator;
import ec3.common.tile.TileMIM;
import ec3.common.tile.TileMINEjector;
import ec3.common.tile.TileMINInjector;
import ec3.common.tile.TileMRUCoil;
import ec3.common.tile.TileMRUCoil_Hardener;
import ec3.common.tile.TileMRUReactor;
import ec3.common.tile.TileMagicalAssembler;
import ec3.common.tile.TileMagicalChest;
import ec3.common.tile.TileMagicalDisplay;
import ec3.common.tile.TileMagicalEnchanter;
import ec3.common.tile.TileMagicalFurnace;
import ec3.common.tile.TileMagicalHopper;
import ec3.common.tile.TileMagicalJukebox;
import ec3.common.tile.TileMagicalMirror;
import ec3.common.tile.TileMagicalQuarry;
import ec3.common.tile.TileMagicalRepairer;
import ec3.common.tile.TileMagicalTeleporter;
import ec3.common.tile.TileMagicianTable;
import ec3.common.tile.TileMagmaticSmelter;
import ec3.common.tile.TileMatrixAbsorber;
import ec3.common.tile.TileMithrilineCrystal;
import ec3.common.tile.TileMithrilineFurnace;
import ec3.common.tile.TileMonsterHarvester;
import ec3.common.tile.TileMonsterHolder;
import ec3.common.tile.TileMoonWell;
import ec3.common.tile.TileNewMIM;
import ec3.common.tile.TileNewMIMCraftingManager;
import ec3.common.tile.TileNewMIMExportNode;
import ec3.common.tile.TileNewMIMExportNode_Persistant;
import ec3.common.tile.TileNewMIMImportNode;
import ec3.common.tile.TileNewMIMImportNode_Persistant;
import ec3.common.tile.TileNewMIMInventoryStorage;
import ec3.common.tile.TileNewMIMScreen;
import ec3.common.tile.TilePlayerPentacle;
import ec3.common.tile.TilePotionSpreader;
import ec3.common.tile.TileRadiatingChamber;
import ec3.common.tile.TileRayTower;
import ec3.common.tile.TileRedstoneTransmitter;
import ec3.common.tile.TileRightClicker;
import ec3.common.tile.TileSolarPrism;
import ec3.common.tile.TileSunRayAbsorber;
import ec3.common.tile.TileUltraFlowerBurner;
import ec3.common.tile.TileUltraHeatGenerator;
import ec3.common.tile.TileWeaponMaker;
import ec3.common.tile.TileWindRune;
import ec3.common.tile.TileecAcceptor;
import ec3.common.tile.TileecBalancer;
import ec3.common.tile.TileecController;
import ec3.common.tile.TileecEjector;
import ec3.common.tile.TileecHoldingChamber;
import ec3.common.tile.TileecRedstoneController;
import ec3.common.tile.TileecStateChecker;
import ec3.utils.cfg.Config;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.config.Configuration;

public class TileRegistry {
	
	public static final List<Class<? extends TileEntity>> cfgDependant = new ArrayList<Class<? extends TileEntity>>();
	
	public static void register() {
		addTileToMapping(TileecController.class);
		addTileToMapping(TileecAcceptor.class);
		addTileToMapping(TileecBalancer.class);
		addTileToMapping(TileecEjector.class);
		addTileToMapping(TileecHoldingChamber.class);
		addTileToMapping(TileecRedstoneController.class);
		addTileToMapping(TileecStateChecker.class);
		addTileToMapping(TileRayTower.class);
		addTileToMapping(TileCorruption.class);
		addTileToMapping(TileMoonWell.class);
		addTileToMapping(TileSolarPrism.class);
		addTileToMapping(TileSunRayAbsorber.class);
		addTileToMapping(TileColdDistillator.class);
		addTileToMapping(TileFlowerBurner.class);
		addTileToMapping(TileHeatGenerator.class);
		addTileToMapping(TileEnderGenerator.class);
		addTileToMapping(TileMagicianTable.class);
		addTileToMapping(TileMagicalQuarry.class);
		addTileToMapping(TileMonsterHolder.class);
		addTileToMapping(TilePotionSpreader.class);
		addTileToMapping(TileMagicalEnchanter.class);
		addTileToMapping(TileMonsterHarvester.class);
		addTileToMapping(TileMagicalRepairer.class);
		addTileToMapping(TileMatrixAbsorber.class);
		addTileToMapping(TileRadiatingChamber.class);
		addTileToMapping(TileMagmaticSmelter.class);
		addTileToMapping(TileMagicalJukebox.class);
		addTileToMapping(TileElementalCrystal.class);
		addTileToMapping(TileCrystalFormer.class);
		addTileToMapping(TileCrystalController.class);
		addTileToMapping(TileCrystalExtractor.class);
		addTileToMapping(TileChargingChamber.class);
		addTileToMapping(TileMagicalTeleporter.class);
		addTileToMapping(TileMagicalFurnace.class);
		addTileToMapping(TileEmberForge.class);
		addTileToMapping(TileMRUCoil_Hardener.class);
		addTileToMapping(TileMRUCoil.class);
		addTileToMapping(TileCorruptionCleaner.class);
		addTileToMapping(TileMRUReactor.class);
		//addTileToMapping(TileMINEjector.class);
		//addTileToMapping(TileAMINEjector.class);
		//addTileToMapping(TileMINInjector.class);
		//addTileToMapping(TileAMINInjector.class);
		//addTileToMapping(TileMIM.class);
		addTileToMapping(TileDarknessObelisk.class);
		addTileToMapping(TileUltraHeatGenerator.class);
		addTileToMapping(TileUltraFlowerBurner.class);
		addTileToMapping(TileMagicalAssembler.class);
		addTileToMapping(TileMagicalMirror.class);
		addTileToMapping(TileMagicalDisplay.class);
		addTileToMapping(TileMithrilineCrystal.class);
		addTileToMapping(TileMithrilineFurnace.class);
		addTileToMapping(TilePlayerPentacle.class);
		addTileToMapping(TileWindRune.class);
		addTileToMapping(TileRightClicker.class);
		addTileToMapping(TileRedstoneTransmitter.class);
		addTileToMapping(TileMagicalHopper.class);
		addTileToMapping(TileDemonicPentacle.class);
		addTileToMapping(TileWeaponMaker.class);
		addTileToMapping(TileFurnaceMagic.class);
		addTileToMapping(TileMagicalChest.class);
		addTileToMapping(TileNewMIMInventoryStorage.class);
		addTileToMapping(TileNewMIM.class);
		addTileToMapping(TileNewMIMScreen.class);
		addTileToMapping(TileNewMIMCraftingManager.class);
		addTileToMapping(TileNewMIMExportNode.class);
		addTileToMapping(TileNewMIMImportNode.class);
		addTileToMapping(TileAdvancedBlockBreaker.class);
		addTileToMapping(TileNewMIMExportNode_Persistant.class);
		addTileToMapping(TileNewMIMImportNode_Persistant.class);
		addTileToMapping(TileCrafter.class);
		addTileToMapping(TileCreativeMRUSource.class);
		addTileToMapping(TileAnimalSeparator.class);
	}
	
	public static void addTileToMapping(Class<? extends TileEntity> tile) {
		GameRegistry.registerTileEntity(tile, "ec3:"+tile.getCanonicalName());
		try {
			if(tile.getMethod("setupConfig", Configuration.class) != null) {
				cfgDependant.add(tile);
				tile.getMethod("setupConfig", Configuration.class).invoke(null, Config.config);
			}
		}
		catch(Exception e) {
			return;
		}
	}
}
