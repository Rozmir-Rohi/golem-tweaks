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

@Mod(modid = "GolemTweaks", name = "Golem Tweaks", version = "1.3.1")
public class GolemTweaks
{
	@SidedProxy(clientSide = "rozmir.proxy.ClientProxy", serverSide = "rozmir.proxy.CommonProxy")
	
	public static CommonProxy proxy;
	
	public static Configuration configFile;
	
	private static boolean isThaumcraftLoaded;
	
	public static boolean enableSnowGolemBuffs;
	public static int snowGolemHealAmount;
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
    	
    	enableSnowGolemBuffs = configFile.getBoolean("enableSnowGolemBuffs", "general", false, "If True: Snow Golems will have 8 HP and will have a small chance to freeze mobs and deal 1 damage with their snowballs. They will also have a chance to deal more damage to Blazes.");
		
    	snowGolemHealAmount = configFile.getInt("snowGolemHealAmount", "general", 1, 0, 8, "The amount of HP Snow Golems should heal by when they are given a Snowball. This can be disabled by setting it to 0.");
    	
    	enableIronGolemsAlwaysPassive = configFile.getBoolean("enableIronGolemsAlwaysPassive", "general", true, "If True: Iron Golems will always be passive towards the player, even if they are hit by them.");
    	
    	ironGolemHealAmount = configFile.getInt("ironGolemHealAmount", "general", 25, 0, 100, "The amount of HP Iron Golems should heal by when they are given an Iron Ingot (Vanilla MC 1.15: 25). This can be disabled by setting it to 0.");
    	
    	enableIronGolemCracking = configFile.getBoolean("enableIronGolemCracking", "general", true, "If True: Iron Golems will have thier cracking textures from 1.16 when they are hurt, they will also make their cracking sound in different damage stages. This will also override textures from texture packs for Iron Golems. The texture part of this setting works on the client side, so it can be used without affecting worlds/servers.");
    	
		if (configFile.hasChanged()) {configFile.save();}
	}
}