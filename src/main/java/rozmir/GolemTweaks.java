package rozmir;

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

@Mod(modid = "GolemTweaks", name = "Golem Tweaks", version = "1.3")
public class GolemTweaks
{
	@SidedProxy(clientSide = "rozmir.proxy.ClientProxy", serverSide = "rozmir.proxy.CommonProxy")
	
	public static CommonProxy proxy;
	
	public static Configuration configFile;
	
	public static boolean enableSnowGolemBuffs;
	public static boolean enableSnowGolemHealFromSnowBalls;
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
    }
    
    public static void syncConfigSettings()
    {
    	
    	enableSnowGolemBuffs = configFile.getBoolean("enableSnowGolemBuffs", "general", false, "If True: Snow Golems will have 8 HP and will have a small chance to freeze mobs and deal 1 damage with their snowballs. They will also have a chance to deal more damage to blazes.");
		
    	enableSnowGolemHealFromSnowBalls = configFile.getBoolean("enableSnowGolemHealFromSnowBalls", "general", true, "If True: Snow Golems can be healed by being given Snowballs, they will also heal if they are struck by a Snowball.");
    	
    	enableIronGolemsAlwaysPassive = configFile.getBoolean("enableIronGolemsAlwaysPassive", "general", true, "If True: Iron Golems will always be passive towards the player, even if they are hit by them.");
    	
    	ironGolemHealAmount = configFile.getInt("ironGolemHealAmount", "general", 25, 0, 100, "The amount of HP Iron Golems should heal by when they are given Iron Ingots (Vanilla MC 1.15 Default: 25).");
    	
    	enableIronGolemCracking = configFile.getBoolean("enableIronGolemCracking", "general", true, "If True: Iron Golems will have thier cracking textures from 1.16 when they are hurt. This will also override textures from texture packs for Iron Golems.");
    	
		if (configFile.hasChanged()) {configFile.save();}
	}
}