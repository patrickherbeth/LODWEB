
setwd("C:/Users/fdurao/workspace/LODWEB/")

mrrTable <- read_excel("mrr.xlsx",1)

# Create Line Chart

# convert factor to numeric for convenience 
#Orange$Tree <- as.numeric(Orange$Tree) 
#ntrees <- max(Orange$Tree)

mrrTable$USERID <- as.numeric(mrrTable$USERID) 
nUsers <- max(mrrTable$USERID)


# get the range for the x and y axis 
#xrange <- range(Orange$age) 
#yrange <- range(Orange$circumference) 

xrange <- range(mrrTable$MRR) 
yrange <- range(mrrTable$CNN) 

# set up the plot 
plot(xrange, yrange, type="n", xlab="MRR",ylab="CNN Size" ) 
colors <- rainbow(nUsers) 
linetype <- c(1:nUsers) 
plotchar <- seq(20,20+nUsers,1)

# add lines 
for (i in 1:nUsers) { 
  
  aux <- subset(mrrTable, USERID==i) 
  
  lines(aux$MRR, aux$CNN, type="b", lwd=1.5,lty=linetype[i], col=colors[i], pch=plotchar[i]) 
} 

# add a title and subtitle 
title("MRR x CNN")

# add a legend 
legend(xrange[1], yrange[2], 1:nUsers, cex=0.8, col=colors,pch=plotchar, lty=linetype, title="MRR")

