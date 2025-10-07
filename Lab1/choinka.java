public class choinka 
{
    public static void main(String[] args) 
	{
		if (args.length != 1)
		return;
	
		int wysokosc = Integer.parseInt(args[0]);
			
        for(int i = 0; i < wysokosc; ++i) 
		{
            for(int j = 0; j < i; ++j) 
			{
                System.out.print("*");
            }
            System.out.println();
        }
    }
}
