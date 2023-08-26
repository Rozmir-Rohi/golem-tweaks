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
    
    public boolean canAttackClass(Class entity_class)
    {
    	if (EntityPlayer.class.isAssignableFrom(entity_class) || SnowGolemExtension.class.isAssignableFrom(entity_class))
    	{
    		return false;
    	}
        		
    	else
    	{
    		return super.canAttackClass(entity_class);
    	}
    }
	
}