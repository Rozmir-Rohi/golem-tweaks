package golem_tweaks.proxy;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.EntityRegistry;
import golem_tweaks.GolemTweaks;
import golem_tweaks.GolemTweaksThaumcraftAspects;
import golem_tweaks.entity_extensions.EntityIronGolemExtension;
import golem_tweaks.entity_extensions.EntitySnowGolemExtension;
import golem_tweaks.entity_extensions.EntitySnowballExtension;
import golem_tweaks.events.ReplaceVanillaGolemsEvent;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {
	
	private static boolean isThaumcraftLoaded;
	
	public void registerEntityRenderers()
	{
		//Nothing is supposed to be here, renderers are handled through the client proxy.
	}
}
