package rozmir.entity_extensions;

import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;

public class SnowGolemExtension extends EntitySnowman
{	

	
	public SnowGolemExtension(final World world) {
		super(world);
	}
	
	public void onLivingUpdate()
    {
        super.onLivingUpdate();
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.posY);
        int k = MathHelper.floor_double(this.posZ);
        
        if (this.isWet())
        {
            this.attackEntityFrom(DamageSource.drown, 1.0F);
        }
        
        if (this.worldObj.getBiomeGenForCoords(i, k).getFloatTemperature(i, j, k) > 1.0F)
        {
            this.attackEntityFrom(DamageSource.onFire, 1.0F);
        }

        for (int l = 0; l < 4; ++l)
        {
            i = MathHelper.floor_double(this.posX + (double)((float)(l % 2 * 2 - 1) * 0.25F));
            j = MathHelper.floor_double(this.posY);
            k = MathHelper.floor_double(this.posZ + (double)((float)(l / 2 % 2 * 2 - 1) * 0.25F));

            if (this.worldObj.getBlock(i, j, k).getMaterial() == Material.air && this.worldObj.getBiomeGenForCoords(i, k).getFloatTemperature(i, j, k) < 0.8F && Blocks.snow_layer.canPlaceBlockAt(this.worldObj, i, j, k))
            {
                this.worldObj.setBlock(i, j, k, Blocks.snow_layer);
            }
        }
    }
	
	protected String getHurtSound()
    {
        return "dig.snow";
    }
	
	protected String getDeathSound()
    {
        return "dig.snow";
    }
		
	public boolean interact(EntityPlayer player)
	{
	    ItemStack itemstack = player.inventory.getCurrentItem();

	    if (itemstack != null)
	    {
	    	if (itemstack.getItem() == Items.snowball) //detect if player is holding healing item
	    	{
	    		if (!player.capabilities.isCreativeMode)
	            {
	                --itemstack.stackSize; //decrease the amount by one
	            }
	    		
	    		healSnowGolem();
	    		
	            if (itemstack.stackSize <= 0)
	            {
	            	player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
	            }

	            return true;
	        } 
	    }
	    return super.interact(player);    
	}
	
	
	public boolean attackEntityFrom(DamageSource source, float dmg)
	{
        Entity entity = source.getEntity();
        Entity sourceDamage = source.getSourceOfDamage();
        
        if ((sourceDamage != null) && (sourceDamage instanceof EntitySnowball))
        {
        	this.healSnowGolem();
        	return true;
        }
        
        return super.attackEntityFrom(source, dmg);
    }
    
	public void healSnowGolem()
	{
		if (this.getHealth() < this.getMaxHealth())
		{
			this.heal(1);
			this.extinguish();
			this.playSound("step.snow", 0.75F, 1.0F); //play healing sound
		}
	}
	
	
}


	