package rozmir;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import rozmir.entity_extensions.EntityIronGolemExtension;
import rozmir.entity_extensions.EntitySnowGolemExtension;
import rozmir.entity_extensions.EntitySnowballExtension;
import rozmir.events.ReplaceVanillaGolemsEvent;
import rozmir.proxy.CommonProxy;

@Mod(modid = "GolemTweaks", name = "Golem Tweaks", version = "1.3.2")
public class GolemTweaks
{
	@SidedProxy(clientSide = "rozmir.proxy.ClientProxy", serverSide = "rozmir.proxy.CommonProxy")
	
	public static CommonProxy proxy;
	
	public static Configuration configFile;
	
	private static boolean isThaumcraftLoaded;
	
	public static boolean enableSnowGolemBuffs;
	public static int snowGolemHealAmount;
	public static int snowGolemSlownessAttackDuration;
	public static int snowGolemSlownessAttackPotionLevel;
	public static int snowGolemSlownessAttackChanceForNormalMobs;
	public static int snowGolemSlownessAttackDamageToNormalMobs;
	public static int snowGolemSlownessAttackChanceForBlazes;
	public static int snowGolemSlownessAttackDamageToBlazes;
	public static boolean enableIronGolemsAlwaysPassive;
	public static int ironGolemHealAmount;
	public static boolean enableIronGolemCracking;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		configFile = new Configuration(event.getSuggestedConfigurationFile());
		syncConfigSettings();
	}
	
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        int modEntityID = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerModEntity((Class) EntityIronGolemExtension.class, "IronGolem", modEntityID++, (Object)this, 50, 2, true);
        EntityRegistry.registerModEntity((Class) EntitySnowGolemExtension.class, "SnowGolem", modEntityID++, (Object)this, 50, 2, true);
        
        if (enableSnowGolemBuffs)
        {
        	EntityRegistry.registerModEntity((Class) EntitySnowballExtension.class, "Snowball", modEntityID++, (Object)this, 50, 2, true);
        }
        
        MinecraftForge.EVENT_BUS.register((Object)new ReplaceVanillaGolemsEvent());
        
        proxy.registerEntityRenderers();
        
        isThaumcraftLoaded = Loader.isModLoaded("Thaumcraft");
        
        if (isThaumcraftLoaded) {GolemTweaksThaumcraftAspects.addThaumcraftAspects();};
    }
    
    public static void syncConfigSettings()
    {
    	
    	enableSnowGolemBuffs = configFile.getBoolean("enableSnowGolemBuffs", "general", false, "If True: Snow Golems will have 8 HP and will have a small chance to apply slowness to mobs and deal damage with their snowballs. This can be further configured through the snowGolemFreezeAttack settings.");
		
    	snowGolemHealAmount = configFile.getInt("snowGolemHealAmount", "general", 1, 0, 8, "The amount of HP Snow Golems should heal by when they are given a Snowball. This can be disabled by setting it to 0.");
    	
    	enableIronGolemsAlwaysPassive = configFile.getBoolean("enableIronGolemsAlwaysPassive", "general", true, "If True: Iron Golems will always be passive towards the player, even if they are hit by them.");
    	
    	ironGolemHealAmount = configFile.getInt("ironGolemHealAmount", "general", 25, 0, 100, "The amount of HP Iron Golems should heal by when they are given an Iron Ingot (Vanilla MC 1.15: 25). This can be disabled by setting it to 0.");
    	
    	enableIronGolemCracking = configFile.getBoolean("enableIronGolemCracking", "general", true, "If True: Iron Golems will have thier cracking textures from 1.16 when they are hurt, they will also make their cracking sound in different damage stages. This will also override textures from texture packs for Iron Golems.");
    	
    	
    	
    	snowGolemSlownessAttackDuration = configFile.getInt("snowGolemSlownessAttackDuration", "snow_golem_slowness_attack", 2, 0, (int) Integer.MAX_VALUE, "Only works if 'enableSnowGolemBuffs' is true. How long the slowness applied from by the Snow Golem's attack should last in seconds.");
    	
    	snowGolemSlownessAttackPotionLevel = configFile.getInt("snowGolemSlownessAttackPotionLevel", "snow_golem_slowness_attack", 10, 0, (int) Integer.MAX_VALUE, "Only works if 'enableSnowGolemBuffs' is true. What strength the slowness applied from the Snow Golem's attack should be. Usually a strength of 6 or above freezes most vanilla mobs.");
    	
    	snowGolemSlownessAttackChanceForNormalMobs = configFile.getInt("snowGolemSlownessAttackChanceForNormalMobs", "snow_golem_slowness_attack", 10, 0, 100, "Only works if 'enableSnowGolemBuffs' is true. The percentage chance that Snow Golems will do their slowness attack to any mob except Blazes (for example, 10 = 10% percent chance).");
    	
    	snowGolemSlownessAttackDamageToNormalMobs = configFile.getInt("snowGolemSlownessAttackDamageToNormalMobs", "snow_golem_slowness_attack", 1, 0, (int) Integer.MAX_VALUE, "Only works if 'enableSnowGolemBuffs' is true. How much damage Snow Golems should deal to any mob except Blazes in their slowness attack.");
    	
    	snowGolemSlownessAttackDamageToBlazes = configFile.getInt("snowGolemSlownessAttackDamageToBlazes", "snow_golem_slowness_attack", 9, 0, (int) Integer.MAX_VALUE, "Only works if 'enableSnowGolemBuffs' is true. How much damage Snow Golems should deal to Blazes in their slowness attack.");
    	
    	snowGolemSlownessAttackChanceForBlazes = configFile.getInt("snowGolemSlownessAttackChanceForBlazes", "snow_golem_slowness_attack", 25, 0, 100, "Only works if 'enableSnowGolemBuffs' is true. The percentage chance that Snow Golems will do their slowness attack to Blazes (for example, 10 = 10% percent chance).");
    	
		if (configFile.hasChanged()) {configFile.save();}
	}
}