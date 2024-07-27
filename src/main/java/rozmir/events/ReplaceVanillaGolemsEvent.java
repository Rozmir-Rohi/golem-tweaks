package rozmir.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import rozmir.entity_extensions.EntityIronGolemExtension;
import rozmir.entity_extensions.EntitySnowGolemExtension;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraftforge.event.entity.living.LivingEvent;

public class ReplaceVanillaGolemsEvent
{
    @SubscribeEvent
    public void livingUpdate(final LivingEvent.LivingUpdateEvent event)
	{
    	EntityLivingBase entityLivingBase = event.entityLiving;
    	
    	if(!entityLivingBase.worldObj.isRemote)
    	{
	        if (entityLivingBase.getClass() == EntityIronGolem.class)
			{
	            final EntityIronGolemExtension ironGolem = new EntityIronGolemExtension(entityLivingBase.worldObj);
	            ironGolem.copyLocationAndAnglesFrom((Entity) entityLivingBase);
	            ironGolem.onSpawnWithEgg((IEntityLivingData) null);
	            
	            if (((EntityIronGolem) entityLivingBase).isPlayerCreated())
	            {
	            	ironGolem.setPlayerCreated(true);
	            }
	            
	            ironGolem.worldObj.spawnEntityInWorld((Entity)ironGolem);
	            entityLivingBase.setDead();
	        }
	        
	        if (entityLivingBase.getClass() == EntitySnowman.class)
			{
	            final EntitySnowGolemExtension snowGolem = new EntitySnowGolemExtension(entityLivingBase.worldObj);
	            snowGolem.copyLocationAndAnglesFrom((Entity) entityLivingBase);
	            snowGolem.onSpawnWithEgg((IEntityLivingData) null);
	            snowGolem.worldObj.spawnEntityInWorld((Entity)snowGolem);
	            entityLivingBase.setDead();
	        }
    	}
    }
}