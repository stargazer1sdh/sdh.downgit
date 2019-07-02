package chdistill;


public class zMain {
	public static void main(String[] args) {
		String l = "package chdistill.source;\n" + 
				"\n" + 
				"/**\n" + 
				" * This is our first  test class.\n" + 
				" * @author Beat Fluri\n" + 
				" */\n" + 
				"public class L {\n" + 
				"	void f() { \n" + 
				"		int b=1 +2; \n" + 
				"		int a=1 +2; \n" + 
				"	}\n" + 
				"} ";
		String ll = "package chdistill.source;\n" + 
				"\n" + 
				"/**\n" + 
				" * This is our first  test class.\n" + 
				" * @author Beat Fluri\n" + 
				" */\n" + 
				"public class L {\n" + 
				"	void f() { \n" + 
				"		int b=1 +2; \n" + 
				"		int a=1 +2; \n" + 
				"	}\n" + 
				"} ";
		String l2 = "package chdistill.source;\n" + 
				"/**\n" + 
				" * This is our first  test class.\n" + 
				" * @author Beat Fluri\n" + 
				" */\n" + 
				"public class L {\n" + 
				"	void f() { \n" + 
				"		int b=1 +2; \n" + 
				"		int a=1 +2; \n" + 
				"	}\n" + 
				"} ";
		String r = "package chdistill.source;\n" + 
				"import uk.gov.gchq.gaffer.parquetstore.utils.SortDataTest;\n" + 
				"public class L {\n" + 
				"//	static {\n" + 
				"//		new SortDataTest().generatePreAggregatedData();\n" + 
				"//		new SortDataTest().param = 1;\n" + 
				"//	}\n" + 
				"	void f() {\n" + 
				"	    SortDataTest dt = new SortDataTest();\n" + 
				"		int a=1+dt.param;\n" + 
				"		dt.generatePreAggregatedData();\n" + 
				"		new SortDataTest().param = 3;\n" + 
				"	}\n" + 
				"}";
		System.out.println(r.substring(262,278+1));
//		System.out.println(l.substring(27,89+1));
//		System.out.println(ll.substring(27,89+1));
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
//		System.out.println(r.substring(222,258+1));
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
//		System.out.println(r.substring(282,312+1));
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
//		System.out.println(r.substring(316,344+1));
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
//		System.out.println(l.substring(123,133+1));
//		System.out.println(ll.substring(123,133+1));
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
//		System.out.println(l.substring(138,148+1));
//		System.out.println(ll.substring(138,148+1));
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
		
		String s = "/*\n" + 
				" * Copyright 2016-2019 Crown Copyright\n" + 
				" *\n" + 
				" * Licensed under the Apache License, Version 2.0 (the \"License\");\n" + 
				" * you may not use this file except in compliance with the License.\n" + 
				" * You may obtain a copy of the License at\n" + 
				" *\n" + 
				" *     http://www.apache.org/licenses/LICENSE-2.0\n" + 
				" *\n" + 
				" * Unless required by applicable law or agreed to in writing, software\n" + 
				" * distributed under the License is distributed on an \"AS IS\" BASIS,\n" + 
				" * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" + 
				" * See the License for the specific language governing permissions and\n" + 
				" * limitations under the License.\n" + 
				" */\n" + 
				"package uk.gov.gchq.gaffer.jobtracker;\n" + 
				"\n" + 
				"import org.apache.commons.lang3.builder.EqualsBuilder;\n" + 
				"import org.apache.commons.lang3.builder.HashCodeBuilder;\n" + 
				"\n" + 
				"import uk.gov.gchq.gaffer.commonutil.CommonConstants;\n" + 
				"import uk.gov.gchq.gaffer.commonutil.ToStringBuilder;\n" + 
				"import uk.gov.gchq.gaffer.operation.OperationChain;\n" + 
				"\n" + 
				"import java.io.Serializable;\n" + 
				"\n" + 
				"/**\n" + 
				" * POJO containing details of a Gaffer job.\n" + 
				" */\n" + 
				"public class JobDetail implements Serializable {\n" + 
				"    private static final long serialVersionUID = -1677432285205724269L;\n" + 
				"    private static final String CHARSET_NAME = CommonConstants.UTF_8;\n" + 
				"    private String parentJobId;\n" + 
				"    private Repeat repeat;\n" + 
				"    private String jobId;\n" + 
				"    private String userId;\n" + 
				"    private JobStatus status;\n" + 
				"    private Long startTime;\n" + 
				"    private Long endTime;\n" + 
				"    private String opChain;\n" + 
				"    private String description;\n" + 
				"\n" + 
				"    public JobDetail() {\n" + 
				"    }\n" + 
				"\n" + 
				"    public JobDetail(final JobDetail oldJobDetail, final JobDetail newJobDetail) {\n" + 
				"        this.jobId = getNewOrOld(oldJobDetail.jobId, newJobDetail.jobId);\n" + 
				"        this.userId = getNewOrOld(oldJobDetail.userId, newJobDetail.userId);\n" + 
				"        this.opChain = getNewOrOld(oldJobDetail.opChain, newJobDetail.opChain);\n" + 
				"        this.description = getNewOrOld(oldJobDetail.description, newJobDetail.description);\n" + 
				"        this.status = getNewOrOld(oldJobDetail.status, newJobDetail.status);\n" + 
				"        this.parentJobId = getNewOrOld(oldJobDetail.parentJobId, newJobDetail.parentJobId);\n" + 
				"        this.repeat = getNewOrOld(oldJobDetail.repeat, newJobDetail.repeat);\n" + 
				"\n" + 
				"        if (null == oldJobDetail.startTime) {\n" + 
				"            this.startTime = System.currentTimeMillis();\n" + 
				"        } else {\n" + 
				"            this.startTime = oldJobDetail.startTime;\n" + 
				"            this.endTime = System.currentTimeMillis();\n" + 
				"        }\n" + 
				"    }\n" + 
				"\n" + 
				"    public JobDetail(final String jobId, final String userId, final OperationChain<?> opChain, final JobStatus jobStatus, final String description) {\n" + 
				"        this(jobId, null, userId, opChain, jobStatus, description);\n" + 
				"    }\n" + 
				"\n" + 
				"    public JobDetail(final String jobId, final String userId, final String opChain, final JobStatus jobStatus, final String description) {\n" + 
				"        this(jobId, null, userId, opChain, jobStatus, description);\n" + 
				"    }\n" + 
				"\n" + 
				"    public JobDetail(final String jobId, final String parentJobId, final String userId, final OperationChain<?> opChain, final JobStatus jobStatus, final String description) {\n" + 
				"        this.jobId = jobId;\n" + 
				"        this.parentJobId = parentJobId;\n" + 
				"        this.userId = userId;\n" + 
				"        this.startTime = System.currentTimeMillis();\n" + 
				"        this.status = jobStatus;\n" + 
				"        this.opChain = null != opChain ? opChain.toOverviewString() : \"\";\n" + 
				"        this.description = description;\n" + 
				"    }\n" + 
				"\n" + 
				"    public JobDetail(final String jobId, final String parentJobId, final String userId, final String opChain, final JobStatus jobStatus, final String description) {\n" + 
				"        setOpChain(opChain);\n" + 
				"        this.jobId = jobId;\n" + 
				"        this.userId = userId;\n" + 
				"        this.startTime = System.currentTimeMillis();\n" + 
				"        this.status = jobStatus;\n" + 
				"        this.description = description;\n" + 
				"        this.parentJobId = parentJobId;\n" + 
				"    }\n" + 
				"\n" + 
				"    public String getJobId() {\n" + 
				"        return jobId;\n" + 
				"    }\n" + 
				"\n" + 
				"    public void setJobId(final String jobId) {\n" + 
				"        this.jobId = jobId;\n" + 
				"    }\n" + 
				"\n" + 
				"    public String getUserId() {\n" + 
				"        return userId;\n" + 
				"    }\n" + 
				"\n" + 
				"    public void setUserId(final String userId) {\n" + 
				"        this.userId = userId;\n" + 
				"    }\n" + 
				"\n" + 
				"    public JobStatus getStatus() {\n" + 
				"        return status;\n" + 
				"    }\n" + 
				"\n" + 
				"    public void setStatus(final JobStatus status) {\n" + 
				"        this.status = status;\n" + 
				"    }\n" + 
				"\n" + 
				"    public Long getStartTime() {\n" + 
				"        return startTime;\n" + 
				"    }\n" + 
				"\n" + 
				"    public void setStartTime(final Long startTime) {\n" + 
				"        this.startTime = startTime;\n" + 
				"    }\n" + 
				"\n" + 
				"    public Long getEndTime() {\n" + 
				"        return endTime;\n" + 
				"    }\n" + 
				"\n" + 
				"    public void setEndTime(final Long endTime) {\n" + 
				"        this.endTime = endTime;\n" + 
				"    }\n" + 
				"\n" + 
				"    public void setParentJobId(final String parentJobId) {\n" + 
				"        this.parentJobId = parentJobId;\n" + 
				"    }\n" + 
				"\n" + 
				"    public String getParentJobId() {\n" + 
				"        return parentJobId;\n" + 
				"    }\n" + 
				"\n" + 
				"    public String getOpChain() {\n" + 
				"        return opChain;\n" + 
				"    }\n" + 
				"\n" + 
				"    public void setOpChain(final String opChain) {\n" + 
				"        this.opChain = opChain;\n" + 
				"    }\n" + 
				"\n" + 
				"    public String getDescription() {\n" + 
				"        return description;\n" + 
				"    }\n" + 
				"\n" + 
				"    public void setDescription(final String description) {\n" + 
				"        this.description = description;\n" + 
				"    }\n" + 
				"\n" + 
				"    public Repeat getRepeat() {\n" + 
				"        return repeat;\n" + 
				"    }\n" + 
				"\n" + 
				"    public void setRepeat(final Repeat repeat) {\n" + 
				"        this.repeat = repeat;\n" + 
				"    }\n" + 
				"\n" + 
				"    @Override\n" + 
				"    public boolean equals(final Object obj) {\n" + 
				"        if (this == obj) {\n" + 
				"            return true;\n" + 
				"        }\n" + 
				"        if (null == obj || getClass() != obj.getClass()) {\n" + 
				"            return false;\n" + 
				"        }\n" + 
				"        final JobDetail jobDetail = (JobDetail) obj;\n" + 
				"        return new EqualsBuilder()\n" + 
				"                .append(jobId, jobDetail.jobId)\n" + 
				"                .append(userId, jobDetail.userId)\n" + 
				"                .append(opChain, jobDetail.opChain)\n" + 
				"                .append(startTime, jobDetail.startTime)\n" + 
				"                .append(endTime, jobDetail.endTime)\n" + 
				"                .append(status, jobDetail.status)\n" + 
				"                .append(description, jobDetail.description)\n" + 
				"                .append(parentJobId, jobDetail.parentJobId)\n" + 
				"                .append(repeat, jobDetail.repeat)\n" + 
				"                .isEquals();\n" + 
				"    }\n" + 
				"\n" + 
				"    @Override\n" + 
				"    public int hashCode() {\n" + 
				"        return new HashCodeBuilder(23, 53)\n" + 
				"                .append(jobId)\n" + 
				"                .append(userId)\n" + 
				"                .append(opChain)\n" + 
				"                .append(startTime)\n" + 
				"                .append(endTime)\n" + 
				"                .append(status)\n" + 
				"                .append(description)\n" + 
				"                .append(parentJobId)\n" + 
				"                .append(repeat)\n" + 
				"                .toHashCode();\n" + 
				"    }\n" + 
				"\n" + 
				"    @Override\n" + 
				"    public String toString() {\n" + 
				"        return new ToStringBuilder(this)\n" + 
				"                .append(\"jobId\", jobId)\n" + 
				"                .append(\"userId\", userId)\n" + 
				"                .append(\"status\", status)\n" + 
				"                .append(\"startTime\", startTime)\n" + 
				"                .append(\"endTime\", endTime)\n" + 
				"                .append(\"opChain\", opChain)\n" + 
				"                .append(\"description\", description)\n" + 
				"                .append(\"parentJobId\", parentJobId)\n" + 
				"                .append(\"repeat\", repeat)\n" + 
				"                .toString();\n" + 
				"    }\n" + 
				"\n" + 
				"    private <T> T getNewOrOld(final T oldValue, final T newValue) {\n" + 
				"        return null == newValue ? oldValue : newValue;\n" + 
				"    }\n" + 
				"}";
		System.out.println(s.substring(3185,3249+1));
		System.out.println(s.substring(4780,4802+1));
		System.out.println(s.substring(4698,4712+1));
		System.out.println(s.substring(1391,1413+1));
	}

}
