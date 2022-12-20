package rozmir.entity_extensions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.World;
import net.minecraft.entity.monster.EntityIronGolem;

public class IronGolemExtension extends EntityIronGolem
{
    private int attackTimer;
    
    public IronGolemExtension(final World world) {
        super(world);
    }
     
    public boolean interact(EntityPlayer player)
	{
	    ItemStack itemstack = player.inventory.getCurrentItem();

	    if (itemstack != null)
	    {
	    	if (itemstack.getItem() == Items.iron_ingot && this.getHealth() < this.getMaxHealth()) //detect if player is holding healing item
	    	{
	    		if (!player.capabilities.isCreativeMode)
	            {
	                --itemstack.stackSize; //decrease the amount by one
	            }
	    		
	    		this.heal(4);
	    		
	    		this.playSound("random.anvil_use", 0.75F, 1.0F); //play healing sound
	    		
	            if (itemstack.stackSize <= 0)
	            {
	            	player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
	            }

	            return true;
	        } 
	    }
	    return super.interact(player);    
	}
    
    public boolean attackEntityAsMob(Entity entity)
    {
		if (!(entity instanceof SnowGolemExtension) && !(entity instanceof EntityPlayer))
		{
	        this.worldObj.setEntityState(this, (byte)4);
	        boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float)(7 + this.rand.nextInt(15)));

	        if (flag)
	        {
	            entity.motionY += 0.4000000059604645D;
	        }

	        this.playSound("mob.irongolem.throw", 1.0F, 1.0F);
	        return flag;
		}
		
		else 
		{
			this.setAttackTarget((EntityLivingBase)null);  //ignore attack
			return true;
		}
        
    }
	
}