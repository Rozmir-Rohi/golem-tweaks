package rozmir;

import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class GolemTweaksThaumcraftAspects {
	public static void addThaumcraftAspects()
	{
		ThaumcraftApi.registerEntityTag("GolemTweaks.IronGolem", new AspectList().add(Aspect.METAL, 16).add(Aspect.EARTH, 12));
		ThaumcraftApi.registerEntityTag("GolemTweaks.SnowGolem", new AspectList().add(Aspect.COLD, 6));
	}
}
