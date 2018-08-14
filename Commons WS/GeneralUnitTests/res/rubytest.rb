
require 'java'
$CLASSPATH << "GeneralUnitTests"; 

myclass = JavaUtilities.get_proxy_class('tests.scripts.JRubyTest')

@myclass  = myclass.new


def write(msg) 
	#puts msg + " world" + " xd"
  @myclass.hey()
end



def anotherFunction() 
	# do something 
end 
