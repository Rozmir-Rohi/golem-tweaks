package rozmir.entity_extensions;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntitySnowballExtension extends EntitySnowball {
	   private int freezeChance;

   public EntitySnowballExtension(World world) {
      super(world);
   }

   public EntitySnowballExtension(World world, EntityLivingBase entity) {
      super(world, entity);
   }

   protected void onImpact(MovingObjectPosition position)
   {   
		if (position.entityHit != null)
		{
			freezeChance = rand.nextInt(100);
			
			Entity entityThatHasBeenHitByThisSnowBall = position.entityHit;
			
			float damageToInflict = 0;
			
			if (entityThatHasBeenHitByThisSnowBall instanceof EntityBlaze)
			{
				damageToInflict = 3;
				
				if (freezeChance <= 25)
				{
					damageToInflict = 9;
				}
			}
			
			if (
					freezeChance <= 10
					&& !(entityThatHasBeenHitByThisSnowBall instanceof EntityIronGolemExtension)
					&& !(entityThatHasBeenHitByThisSnowBall instanceof EntitySnowGolemExtension)
					&& !(entityThatHasBeenHitByThisSnowBall instanceof EntityPlayer)
					&& !(entityThatHasBeenHitByThisSnowBall  instanceof EntityBlaze)
				)
			{
				damageToInflict = 1;
			}

	
			entityThatHasBeenHitByThisSnowBall.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), damageToInflict);
			
			if (damageToInflict > 0)
			{
				((EntityLivingBase)entityThatHasBeenHitByThisSnowBall).addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 30, 150));
			}
		}

      for(int index = 0; index < 8; ++index)
      {
         worldObj.spawnParticle("snowballpoof", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
      }

      if (!worldObj.isRemote)
      {
         setDead();
      }

   }
}
